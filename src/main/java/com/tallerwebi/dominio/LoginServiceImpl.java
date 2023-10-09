package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserAlreadyExistException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.presentacion.dto.LoginDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository servicioLoginDao){
        this.userRepository = servicioLoginDao;
    }

    @Override
    public MobileUser searchUser(String email, String password) {
        return userRepository.findUserByMailAndPassword(email, password);
    }

    @Override
    public void registerUser(LoginDataDTO request) throws UserNotFoundException {
        MobileUser userEncontrado =  userRepository.findUserByMailAndPassword(request.getEmail(), request.getPassword());
        if(userEncontrado != null){
            throw new UserAlreadyExistException();
        }
        MobileUser user = new MobileUser(
                request.getEmail(),
                request.getPassword(),
                request.getRol(),
                request.getName(),
                request.getNickName());
        userRepository.save(user);
    }

}

