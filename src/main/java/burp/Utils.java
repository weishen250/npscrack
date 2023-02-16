package burp;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;




/**
 * Project: fakeIP
 * Date:2021/5/21 上午11:30
 *
 * @author CoolCat
 * @version 1.0.0
 * Github:https://github.com/TheKingOfDuck
 * When I wirting my code, only God and I know what it does. After a while, only God knows.
 */
public class Utils {



    public static void EditIndex(IContextMenuInvocation iContextMenuInvocation,String Func) throws MalformedURLException {
        // 获取当前选择的消息
        IHttpRequestResponse currentRequest = iContextMenuInvocation.getSelectedMessages()[0];

        // 获取请求信息
        IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(currentRequest);

        // 更改URL路径
        URL newUrl = new URL(requestInfo.getUrl().getProtocol(), requestInfo.getUrl().getHost(), requestInfo.getUrl().getPort(), Func);
        String newUrlString = newUrl.toString();

        // 替换原始请求中的URL路径为新的URL路径
        byte[] request = currentRequest.getRequest();
        String requestString = new String(request, StandardCharsets.UTF_8);
        requestString = requestString.replaceFirst(requestInfo.getUrl().getPath(), newUrl.getPath());
        requestString = requestString.replace(requestInfo.getUrl().toString(), newUrlString);
        currentRequest.setRequest(requestString.getBytes(StandardCharsets.UTF_8));
    }






    public static void AddNpsCooki(IHttpRequestResponse iHttpRequestResponse, String auth_key,Long timestamp) {

        //获取原请求信息
        IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(iHttpRequestResponse);
        List<IParameter> params = requestInfo.getParameters();
        byte[] request = iHttpRequestResponse.getRequest();


        // 构建新参数
        String parameterName1 = "auth_key";
        String parameterValue1 = auth_key;
        IParameter newParameter1 = BurpExtender.helpers.buildParameter(parameterName1, parameterValue1, IParameter.PARAM_URL);

        String parameterName2 = "timestamp";
        String parameterValue2 = String.valueOf(timestamp);
        IParameter newParameter2 = BurpExtender.helpers.buildParameter(parameterName2, parameterValue2, IParameter.PARAM_URL);

        // 将新参数添加到参数列表中
        params.add(newParameter1);
        params.add(newParameter2);

        byte[] newRequest = BurpExtender.helpers.updateParameter(request, newParameter1);
        newRequest = BurpExtender.helpers.updateParameter(newRequest, newParameter2);
        iHttpRequestResponse.setRequest(newRequest);


    }

    private static byte[] getHttpRequestBody(IHttpRequestResponse httpRequestResponse) {
        byte[] request = httpRequestResponse.getRequest();
        IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(request);

        int httpBodyOffset = requestInfo.getBodyOffset();
        int httpBodyLength = request.length - httpBodyOffset;
        byte[] httpBody = new byte[httpBodyLength];
        System.arraycopy(request,httpBodyOffset,httpBody,0,httpBodyLength);
        return httpBody;
    }

    public static String getRandomIp() {

        // ip范围 ref：https://blog.csdn.net/zhengxiongwei/article/details/78486146
        int[][] range = {
                {607649792, 608174079},
                {1038614528, 1039007743},
                {1783627776, 1784676351},
                {2035023872, 2035154943},
                {2078801920, 2079064063},
                {-1950089216, -1948778497},
                {-1425539072, -1425014785},
                {-1236271104, -1235419137},
                {-770113536, -768606209},
                {-569376768, -564133889},
        };

        SecureRandom random = new SecureRandom();
        int index = random.nextInt(10);
        String ip = num2ip(range[index][0] + new SecureRandom().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    public static String num2ip(int ip) {
        int[] b = new int[4];
        String ipStr = "";
        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        ipStr = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);
        return ipStr;
    }
}
