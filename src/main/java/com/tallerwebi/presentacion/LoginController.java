package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.LoginService;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.UserRole;
import com.tallerwebi.presentacion.dto.LoginDataDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new LoginDataDTO());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validate-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") LoginDataDTO loginDataDTO, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        MobileUser usuarioBuscado = (MobileUser) loginService.searchUser(loginDataDTO.getEmail(), loginDataDTO.getPassword());
        if (usuarioBuscado != null) {

            request.getSession().setAttribute("rol", usuarioBuscado.getRol().toString());
            request.getSession().setAttribute("id", usuarioBuscado.getId());
            request.getSession().setAttribute("nickName", usuarioBuscado.getNickName());

            if(usuarioBuscado.getRol() == UserRole.USER){
                return new ModelAndView("redirect:/mobile/home");
            }
            if(usuarioBuscado.getRol() == UserRole.ADMIN){
                return new ModelAndView("redirect:/mobile/admin/reports");
            }
            else{
                return new ModelAndView("redirect:/web/admin");
            }
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("user") LoginDataDTO requestLogin) {
        ModelMap model = new ModelMap();
        try{
            loginService.registerUser(requestLogin);
        } catch (UserNotFoundException e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("new-user", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("new-user", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/new-user", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("user", new LoginDataDTO());
        return new ModelAndView("new-user", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ModelAndView("redirect:/login");
    }

}

