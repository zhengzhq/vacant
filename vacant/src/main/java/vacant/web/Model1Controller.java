package vacant.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/model1")
public class Model1Controller {


	@RequestMapping(value = "/page1", method = GET)
	public String page1() {
		return "/model1/page1";
	}

	@RequestMapping(value = "/page2", method = GET)
	public String page2() {
		return "/model1/page2";
	}
}
