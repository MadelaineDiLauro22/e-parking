package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.User;
import com.tallerwebi.presentacion.dto.LoginDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class LoginServiceImpl implements LoginService {

    private UserRepository servicioLoginDao;

    @Autowired
    public LoginServiceImpl(UserRepository servicioLoginDao){
        this.servicioLoginDao = servicioLoginDao;
    }

    @Override
    public User searchUser(String email, String password) {
        return servicioLoginDao.findUserByMailAndPassword(email, password);
    }

    @Override
    public void registerUser(LoginDataDTO request) throws UserNotFoundException {
        User userEncontrado =  servicioLoginDao.findUserByMailAndPassword(request.getEmail(), request.getPassword());
        if(userEncontrado != null){
            throw new UserNotFoundException();
        }
        MobileUser user = new MobileUser(
                request.getEmail(),
                request.getPassword(),
                request.getRol(),
                request.getName(),
                request.getNickName());
        servicioLoginDao.save(user);
    }

}

