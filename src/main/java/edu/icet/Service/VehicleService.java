package edu.icet.Service;

import edu.icet.Model.Dto.AdminDisplayVehiclesDto;
import edu.icet.Model.Dto.VehicleDTO;
import edu.icet.Model.Dto.VehicleResponseDto;
import edu.icet.Model.Entity.Vehicle;
import edu.icet.Repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    ModelMapper mapper = new ModelMapper();

    @Autowired
    VehicleRepository vehicleRepository;

    private final String UPLOAD_DIR = "uploads/";

     public Vehicle addVehicle(VehicleDTO dto, MultipartFile image) {

        Vehicle vehicle = mapper.map(dto, Vehicle.class);

        vehicle = vehicleRepository.save(vehicle);

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);

        try {
            Files.createDirectories(uploadPath);
            Files.write(uploadPath.resolve(fileName), image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image");
        }

        vehicle.setImagePath(fileName);
        return vehicleRepository.save(vehicle);
    }

     public List<VehicleResponseDto> getAllVehicles() {

        List<Vehicle> vehicles = vehicleRepository.findByStatus("AVAILABLE");

        return vehicles.stream()
                .map(v -> new VehicleResponseDto(
                        v.getVehicleId(),
                        v.getImagePath(),
                        v.getModel(),
                        v.getRegNo(),
                        v.getBrand(),
                        v.getType(),
                        v.getFuelType(),
                        v.getSeat(),
                        v.getDailyRate()
                ))
                .toList();
    }

    public void deleteVehicle(Long vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (vehicle.getImagePath() != null) {
            Path imagePath = Paths.get(UPLOAD_DIR, vehicle.getImagePath());

            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image file", e);
            }
        }
        vehicleRepository.deleteById(vehicleId);
    }


    public void updateVehicle(VehicleDTO dto, MultipartFile image) {

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Update vehicle details
        vehicle.setModel(dto.getModel());
        vehicle.setRegNo(dto.getRegNo());
        vehicle.setBrand(dto.getBrand());
        vehicle.setType(dto.getType());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setSeat(dto.getSeat());
        vehicle.setDailyRate(dto.getDailyRate());

        // Update image only if a new image is selected
        if (image != null && !image.isEmpty()) {

            String fileName =
                    System.currentTimeMillis() + "_" + image.getOriginalFilename();

            Path uploadPath = Paths.get(UPLOAD_DIR);

            try {
                Files.createDirectories(uploadPath);
                Files.write(
                        uploadPath.resolve(fileName),
                        image.getBytes()
                );
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image");
            }

            vehicle.setImagePath(fileName);
        }

        vehicleRepository.save(vehicle);
    }

    public List<VehicleDTO> searchVehicle(String type) {

        List<Vehicle> vehicles = vehicleRepository.findByType(type);

        if (vehicles.isEmpty()) {
            throw new RuntimeException("No vehicles found for type: " + type);
        }

        return vehicles.stream()
                .map(v -> mapper.map(v, VehicleDTO.class))
                .toList();
    }

    public List<AdminDisplayVehiclesDto> adminDisplayVehicles() {

        List<Vehicle> vehicles = vehicleRepository.findAll();

        return vehicles.stream()
                .map(v -> new AdminDisplayVehiclesDto(
                        v.getVehicleId(),
                        v.getImagePath(),
                        v.getModel(),
                        v.getRegNo(),
                        v.getBrand(),
                        v.getType(),
                        v.getFuelType(),
                        v.getSeat(),
                        v.getDailyRate(),
                        v.getStatus()
                ))
                .toList();
    }
}
