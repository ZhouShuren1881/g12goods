package cn.edu.xmu.g12.g12ooadgoods.util;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Luxun 2020
 */
public class Tool {
    public static Object decorateCode(ResponseCode responseCode) {
        return Common.decorateReturnObject(new ReturnObject<>(responseCode, responseCode.getMessage()), null);
    }

    public static Object decorateObject(ReturnObject returnObject) {
        return Common.decorateReturnObject(returnObject, null);
    }

    public static Object decorateCodeOKStatus(ResponseCode responseCode, HttpStatus httpStatus) {
        return Common.decorateReturnObject(new ReturnObject<>(responseCode, responseCode.getMessage()), httpStatus);
    }

    public static Object decorateObjectOKStatus(ReturnObject returnObject, HttpStatus httpStatus) {
        return Common.decorateReturnObject(returnObject, httpStatus);
    }

    private static JwtHelper.UserAndDepart getUserAndDepartFromJwt(HttpServletRequest request) {
        JwtHelper jwt = new JwtHelper();
        return jwt.verifyTokenAndGetClaims(request.getHeader("authorization"));
    }

    public static JwtHelper.UserAndDepart parseJwtAndGetUD(HttpServletRequest request, Long shopId) {
        if (shopId == null) return null;
        var userAndDepart = getUserAndDepartFromJwt(request);
        if (userAndDepart == null) return null;
        var departId = userAndDepart.getDepartId();
        var userId = userAndDepart.getUserId();

        if ( (departId.equals(shopId) && shopId != 0/*过滤普通客人*/) || (userId == 1 && shopId >= 0))
            return userAndDepart; // shopId可以为0

        return null;
    }

    public static Long parseJwtAndGetUser(HttpServletRequest request, Long shopId) {
        var ud = parseJwtAndGetUD(request, shopId);
        if (ud != null) return ud.getUserId();
        return null;
    }

    public static Long parseJwtAndGetUser(HttpServletRequest request) {
        var userAndDepart = getUserAndDepartFromJwt(request);
        if (userAndDepart != null) return userAndDepart.getUserId();
        return null;
    }

    public static Long parseJwtAndGetDepart(HttpServletRequest request, Long shopId) {
        var ud = parseJwtAndGetUD(request, shopId);
        if (ud != null) return ud.getDepartId();
        return null;
    }

    /**
     * 要求shopId对应于一家店铺，可以是 0L，相当于管理员
     */
    public static boolean noAccessToShop(HttpServletRequest request, Long shopId) {
        var ud = parseJwtAndGetUD(request, shopId);
        return ud == null;
    }

    public static LocalDateTime parseDateTime(String text) {
        if (text == null) return null;
        try {
            return LocalDateTime.parse(text);
        } catch (Exception e) {
            try {
                var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                return LocalDateTime.parse(text, formatter);
            } catch (Exception e2) {
                return null;
            }
        }
    }

    public static boolean allNull(Object... args) {
        if (args == null || args.length == 0) return true;
        for (var item : args) if (item != null) return false;
        return true;
    }
}
