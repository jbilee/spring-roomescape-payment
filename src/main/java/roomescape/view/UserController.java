package roomescape.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import roomescape.global.auth.Auth;
import roomescape.member.domain.Role;

@Controller
public class UserController {

    @Value("${odsay.secret}")
    private String odsay;

    @Auth(Role.USER)
    @GetMapping("/reservation")
    public String reservation(
    ) {
        return "reservation";
    }

    @Auth(Role.GUEST)
    @GetMapping("/login")
    public String login(
    ) {
        return "login";
    }

    @Auth(Role.GUEST)
    @GetMapping("/signup")
    public String signUp(
    ) {
        return "signup";
    }

    @Auth(Role.GUEST)
    @GetMapping("/")
    public String index(
            @RequestParam(value = "fx", required = false) Long fx,
            @RequestParam(value = "fy", required = false) Long fy,
            @RequestParam(value = "tx", required = false) Long tx,
            @RequestParam(value = "ty", required = false) Long ty
    ) throws IOException {
        String apiKey = odsay;

        String urlInfo = "https://api.odsay.com/v1/api/searchPubTransPathT?SX=" + fx + "&SY=" + fy + "&EX=" + tx + "&EY=" + ty + "&OPT=1&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

        // http 연결
        URL url = new URL(urlInfo);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        conn.disconnect();

        // 결과 출력
        System.out.println(sb.toString());

        return "index";
    }

    @Auth(Role.USER)
    @GetMapping("/reservation-mine")
    public String reservationMine(
    ) {
        return "reservation-mine";
    }
}
