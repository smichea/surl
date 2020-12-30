package org.surl;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "surl", mixinStandardHelpOptions = true,
      version = "surl 1.0",
  description = "surl is a tool to receive data from a client, "
    +"test if it is what we expect and send back some response (for HTTP).")
public class SurlCli implements Callable<Integer> {

    @Parameters(arity = "1",description = "The url to listen to.")
    String url;

    @Option(names = { "-H", "--header" }, description = "Expected headers to be present in the request")
    String[] headers;

    @Option(names = { "-rH", "--response-header" }, description = "Extra header to include in the HTTP response.")
    String[] responseHeaders;

    SurlServer server= new SurlServer();

    @Override
    public Integer call() throws Exception {
        server.start(8080);
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new SurlCli()).execute(args);
        System.exit(exitCode);
      }
}
