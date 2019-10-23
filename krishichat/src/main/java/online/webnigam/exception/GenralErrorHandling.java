package online.webnigam.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GenralErrorHandling {
	@ExceptionHandler(value = Exception.class)
	public String errorFired(HttpServletRequest request, Exception ex, Model model) {
		System.out.println("Erro in global error handler");
		ex.printStackTrace();
		return "redirect:/home";
	}

}
