import java.util.Scanner;

public class StocksControllerImpl implements StocksController {



  private Scanner input;
  @Override
  public void start() {
    System.out.println("START VIEW PLACEHOLDER");
    System.out.println("MENU CHOICES"
        + "C - Create new portfolio\n"
        + "E: examine portfolio\n"
        + "P - get price of portfolio on certain date\n"
        + "L - load portfolio\n"
        + "exit - close program\n");

    input = new Scanner(System.in);

    String in = input.nextLine();



    System.out.println("Entered " + in);

    in = in.toLowerCase();
    while (! (in.equals("exit"))){

      switch(in){
        case "c":
          System.out.println("VIEW for porfolio creation");
          System.out.println("CALL to portfolio builder");
          break;

        case "e":
          System.out.println("VIEW for porfolio examination");
          System.out.println("Call to ask for input name and return desired portfolio");
          break;

        case "p":
          System.out.println("VIEW for porfolio price");
          System.out.println("CALL to get porfolio name and return price");
          break;

        case "l":
          System.out.println("VIEW for loading portfolio");
          System.out.println("GET porfolio and add it to dir");
          break;

        default:
          System.out.println("Unknown Command, please try again");
          break;
      }
      in = input.nextLine();
      in = in.toLowerCase();
    }

  }
}
