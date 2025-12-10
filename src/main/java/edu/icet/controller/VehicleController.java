package edu.icet.controller;

import edu.icet.Model.Dto.AdminDisplayVehiclesDto;
import edu.icet.Model.Dto.VehicleDTO;
import edu.icet.Model.Dto.VehicleResponseDto;
import edu.icet.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @PostMapping(value = "/addVehicle",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?>addVehicle(
            @RequestPart("vehicle") VehicleDTO dto,
            @RequestPart("image")MultipartFile image
            ){
        System.out.println(dto);
        System.out.println(image.getOriginalFilename());
        vehicleService.addVehicle(dto, image);
        return ResponseEntity.ok("Vehicle Added Successfully!");
    }

    @GetMapping("/allVehicles")
    public List<VehicleResponseDto> getAllVehicles(){

        return vehicleService.getAllVehicles();
    }

    @GetMapping("/adminDisplayVehicles")
    public List<AdminDisplayVehiclesDto> getAdminDisplayVehicles(){
        return vehicleService.adminDisplayVehicles();
    }

    @DeleteMapping("/deleteVehicle/{vehicleId}")
    public void deleteVehicle(@PathVariable Long vehicleId){
          vehicleService.deleteVehicle(vehicleId);
    }

    @PutMapping("/updateVehicle")
    public void updateVehicle(@RequestBody VehicleDTO dto){
        vehicleService.updateVehicle(dto);
    }

    @GetMapping("/searchVehicle/{type}")
    public List<VehicleDTO> searchVehicle(@PathVariable String type){
        return vehicleService.searchVehicle(type);
    }
}
