package com.websystique.springmvc.controller;

import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    UserService service;

    @Autowired
    MessageSource messageSource;

    String currentNickname = "Guest";

    @RequestMapping(value = {"/", "/welcomescreen"}, method = RequestMethod.GET)
    public String welcomeScreen(ModelMap model) {

        model.addAttribute("nickname", currentNickname);
        model.addAttribute("chat", false);
        return "welcomescreen";
    }


    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String logIn(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }


    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logOut(ModelMap model) {
        model.addAttribute("nickname", "Guest");
        currentNickname = "Guest";
        return "welcomescreen";
    }


    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String welcomeScreenAfterLogProcess(@Valid User user, BindingResult result,
                                               ModelMap model) {

        if (result.hasErrors()) {
            return "login";
        }

        if (!service.nicknameExists(user.getNickname())) {
            FieldError nicknameExistsError = new FieldError("user", "nickname", messageSource.getMessage("nickname.not.found", new String[]{user.getNickname()}, Locale.getDefault()));
            result.addError(nicknameExistsError);
            return "login";
        }

        if (!service.isUserPasswordCorrect(user.getNickname(), user.getPassword())) {
            FieldError passwordError = new FieldError("user", "password", messageSource.getMessage("not.correct.password", new String[]{user.getNickname()}, Locale.getDefault()));
            result.addError(passwordError);
            return "login";
        }

        model.addAttribute("nickname", user.getNickname());
        currentNickname = user.getNickname();
        return "welcomescreen";
    }

    /*
     * This method will list all existing employees.
     */
    @RequestMapping(value = {"/manageUsers"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {

        model.addAttribute("nickname", currentNickname);

        List<User> users = service.findAllUsers();
        model.addAttribute("users", users);
        return "allusers";
    }

    /*
     * This method will provide the medium to add a new employee.
     */
    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        return "registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving employee in database. It also validates the user input
     */
    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }

		/*
         * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation
		 * and applying it on field [ssn] of Model class [Employee].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
        if (!service.isUserNicknameUnique(user.getId(), user.getNickname())) {
            FieldError nicknameError = new FieldError("user", "nickname", messageSource.getMessage("non.unique.nickname", new String[]{user.getNickname()}, Locale.getDefault()));
            result.addError(nicknameError);
            return "registration";
        }

        service.saveUser(user);

        model.addAttribute("success", "You " + user.getNickname() + " registered successfully");
        return "success";
    }


    /*
     * This method will provide the medium to update an existing employee.
     */
    @RequestMapping(value = {"/edit-{nickname}-user"}, method = RequestMethod.GET)
    public String editUser(@PathVariable String nickname, ModelMap model) {
        User user = service.findUserByNickname(nickname);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating employee in database. It also validates the user input
     */
    @RequestMapping(value = {"/edit-{nickname}-user"}, method = RequestMethod.POST)
    public String updateEmployee(@Valid User user, BindingResult result,
                                 ModelMap model, @PathVariable String nickname) {

        if (result.hasErrors()) {
            return "registration";
        }

        if (!service.isUserNicknameUnique(user.getId(), user.getNickname())) {
            FieldError nicknameError = new FieldError("user", "nickname", messageSource.getMessage("non.unique.nickname", new String[]{user.getNickname()}, Locale.getDefault()));
            result.addError(nicknameError);
            return "registration";
        }

        service.updateUser(user);

        model.addAttribute("success", "Contact " + user.getFirstName() + " updated successfully");
        return "success";
    }


    /*
     * This method will delete an employee by it's SSN value.
     */
    @RequestMapping(value = {"/delete-{nickname}-user"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String nickname) {
        service.deleteUserByNickname(nickname);
        return "redirect:/manageUsers";
    }

    @RequestMapping(value = {"/chat-{nickname}-user"}, method = RequestMethod.GET)
    public String showChatWindow(@PathVariable String nickname, ModelMap model) {
        List<User> users = service.findAllUsers();
        model.addAttribute("users", users);
        User user = service.findUserByNickname(nickname);
        model.addAttribute("personToChat", user.getNickname());
        model.addAttribute("chat", true);
        model.addAttribute("nickname", currentNickname);

        return "allusers";
    }
}
