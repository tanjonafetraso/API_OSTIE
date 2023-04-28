/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author eqima
 */
@RestController
public class rooterPage {
    @RequestMapping(value = "/")
    public ModelAndView indexPage(){
        return new ModelAndView("index");
    }
}
