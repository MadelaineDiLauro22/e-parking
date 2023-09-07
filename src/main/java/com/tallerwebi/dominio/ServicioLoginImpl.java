package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Usuario;
import com.tallerwebi.presentacion.DatosLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario servicioLoginDao;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario servicioLoginDao){
        this.servicioLoginDao = servicioLoginDao;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        return servicioLoginDao.buscarUsuario(email, password);
    }

    @Override
    public void registrar(DatosLogin request) throws UsuarioExistente {
        Usuario usuarioEncontrado = servicioLoginDao.buscarUsuario(request.getEmail(), request.getPassword());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        //TODO: agregar nombre y nickname al request
        MobileUser user = new MobileUser(
                request.getEmail(),
                request.getPassword(),
                "ADMIN",
                "Admin",
                "admin"
        );
        servicioLoginDao.guardar(user);
    }

}

