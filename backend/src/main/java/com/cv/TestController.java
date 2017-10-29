package cv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;


@Controller
@RequestMapping("/test")
public class TestController {

  @CrossOrigin(origins="http://localhost:8080")
  @RequestMapping(method=RequestMethod.GET)
  public @ResponseBody TestData getTest(
      @RequestParam(value="name", required=false) String name) {
    return new TestData("hello", name);
  }

}
