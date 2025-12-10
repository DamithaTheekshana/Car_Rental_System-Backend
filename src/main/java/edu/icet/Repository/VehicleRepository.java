package edu.icet.Repository;

import edu.icet.Model.Dto.VehicleResponseDto;
import edu.icet.Model.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByType(String type);

    List<Vehicle> findByStatus(String available);
}
