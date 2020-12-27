package cn.edu.xmu.g12.g12ooadgoods.util;

import javax.servlet.http.HttpServletRequest;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Luxun 2020
 */
public class Tool {
    public static ResponseCode checkPageParam(Integer page, Integer pageSize) {
        if ((page == null) != (pageSize == null)) return ResponseCode.FIELD_NOTVALID;
        if (page == null /* && pageSize == null*/) return ResponseCode.OK;
        if (page <= 0 || pageSize <= 0) return ResponseCode.FIELD_NOTVALID;
        return ResponseCode.OK;
    }

    public static Object decorateResponseCode(ResponseCode responseCode) {
        return Common.decorateReturnObject(new ReturnObject<>(responseCode));
    }

    public static Object decorateReturnObject(ReturnObject returnObject) {
        return Common.decorateReturnObject(returnObject);
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
        if (!departId.equals(shopId) && departId != 1 || shopId < 0) return null; // shopId可以为0
        return userAndDepart;
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
     * 要求shopId对应于一家具体的店铺，可以是 1L，相当于管理员
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
                var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(text, formatter);
            } catch (Exception e2) {
                return null;
            }
        }
    }
}
