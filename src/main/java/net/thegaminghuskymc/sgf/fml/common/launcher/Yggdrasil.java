package net.thegaminghuskymc.sgf.fml.common.launcher;

import net.thegaminghuskymc.authlib.Agent;
import net.thegaminghuskymc.authlib.AuthenticationException;
import net.thegaminghuskymc.authlib.yggrasil.YggdrasilAuthenticationService;
import net.thegaminghuskymc.authlib.yggrasil.YggdrasilUserAuthentication;
import org.apache.logging.log4j.LogManager;

import java.net.Proxy;
import java.util.Map;

public class Yggdrasil
{
    public static void login(Map<String, String> args)
    {
        if (!args.containsKey("--username") || !args.containsKey("--password")) return;
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "1").createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(args.get("--username"));
        auth.setPassword(args.remove("--password"));

        try
        {
            auth.logIn();
        }
        catch (AuthenticationException e)
        {
            LogManager.getLogger("FMLTWEAK").error("-- Login failed! {}", e.getMessage(), e);
            throw new RuntimeException(e); // don't set other variables
        }

        args.put("--username",       auth.getSelectedProfile().getName());
        args.put("--uuid",           auth.getSelectedProfile().getId().toString().replace("-", ""));
        args.put("--accessToken",    auth.getAuthenticatedToken());
        args.put("--userProperties", auth.getUserProperties().toString());
    }
}