package edu.icet.Service;

import edu.icet.Model.Dto.VehicleDTO;
import edu.icet.Model.Entity.Vehicle;
import edu.icet.Repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

        Path uploadPath = Paths.get(UPLOAD_DIR);
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory", e);
        }

        // Save image
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        try {
            Files.write(filePath, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }

        // map dto → entity
        Vehicle vehicle = mapper.map(dto, Vehicle.class);
        vehicle.setImagePath(fileName);

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
       return vehicleRepository.findAll();
    }

    public void deleteVehicle(Long vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    public void updateVehicle(VehicleDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        mapper.getConfiguration().setSkipNullEnabled(false);
        mapper.map(dto, vehicle);
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

}
