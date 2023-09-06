package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehiculo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final VehicleRepository vehicleRepository;
    private final RepositorioUsuario repositorioUsuario;

    public ParkingServiceImpl(VehicleRepository vehicleRepository, RepositorioUsuario repositorioUsuario) {
        this.vehicleRepository = vehicleRepository;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public List<Vehiculo> getUserCarsList(Long idUsuario) {
        MobileUser user = (MobileUser) repositorioUsuario.buscarUsuarioPorId(idUsuario);
        return vehicleRepository.obtenerVehiculosPorUsuario(user);
    }
}
