package au.com.utils;

public class ServerSelection {

    private String serverUrl;
    private final String basePath = "data/2.5/";
    private ServerType serverType;

    public enum ServerType {
        LOCAL, DEV
    }

    //if there are many environments
    private final String localServerUrl = "https://api.openweathermap.org/";
    private final String devServerUrl = "https://api.openweathermap.org/";

    public ServerSelection(ServerType serverType) {
        this.serverType = serverType;
            switch (serverType) {
                case LOCAL:
                    System.out.println("Backend Server Selection is Local");
                    serverUrl = localServerUrl;
                    break;
                case DEV:
                    System.out.println("Backend Server Selection is DEV");
                    serverUrl = devServerUrl;
                    break;

                default:
                    System.out.println("Backend Server Selection is LOCAL");
                    serverUrl = localServerUrl;
                    break;
            }
        }
    static public ServerType getServerType(String type) {
        if (type.equalsIgnoreCase("LOCAL")) {
            return ServerType.LOCAL;
        }
        else if (type.equalsIgnoreCase("DEV")) {
            return ServerType.DEV;
        } else {
            return ServerType.LOCAL;
        }
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getBasePath() { return basePath;  }
}
