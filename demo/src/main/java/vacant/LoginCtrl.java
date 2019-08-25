package vacant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginCtrl extends BaseCtrl {

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest req) {
		return "login";
	}

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		return "login";
	}
	
	@Override
	protected String v() {
		return null;
	}

}
