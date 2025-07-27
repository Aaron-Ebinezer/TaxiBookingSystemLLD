import java.util.*;

public class taxiBookingSystem {
    static List<Taxi> taxies = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int customerCounter = 1;

    public static void main(String[] args) {
        System.out.println("Enter number of taxis:");
        int numTaxis = sc.nextInt();
        initializeTaxis(numTaxis);

        while(true){
            System.out.println("\n1.Book Taxi\n2.Display taxi details\n3.Exit");
            System.out.print("Enter your choice:");
            int choice = sc.nextInt();

            switch(choice){
                case 1:
                    bookTaxi();
                    break;
                case 2:
                    displayTaxiDetails();
                    break;
                case 3:
                    System.out.println("Exiting.......");
                    return;
                default:
                    System.out.println("Invalid choice..Try again...");
            }
        }
    }

    public static void initializeTaxis(int n){
        for(int i = 1;i <= n;i++){
            taxies.add(new Taxi(i));
        }
    }

    public static void bookTaxi(){
        int customerId = customerCounter++;
        System.out.print("Enter Pickup Point (A-F): ");
        char pickUp = sc.next().toUpperCase().charAt(0);
        System.out.print("Enter Drop Point (A-F): ");
        char drop = sc.next().toUpperCase().charAt(0);
        System.out.print("Enter Pickup Time (in hours): ");
        int pickupTime = sc.nextInt();

        Taxi selectedTaxi = null;
        int minDistance = Integer.MAX_VALUE;
        for(Taxi taxi : taxies){
            if(taxi.isAvailable(pickupTime)){
                int distance = Math.abs(taxi.currentPoint - pickUp);
                if(distance < minDistance || (distance == minDistance && taxi.totalEarnings < selectedTaxi.totalEarnings)){
                    selectedTaxi = taxi;
                    minDistance = distance;
                }
            }
        }
        if(selectedTaxi == null){
            System.out.println("Booking rejected No taxis available..");
            return;
        }

        int dropTime = pickupTime + Math.abs(drop - pickUp);
        int amount = selectedTaxi.calculateEarnings(pickUp, drop);
        int bookingId = selectedTaxi.bookings.size() + 1;
        Booking booking = new Booking(bookingId, customerId, pickupTime, dropTime, amount, pickUp, drop);
        selectedTaxi.addBooking(booking);
        System.out.println("Taxi-" + selectedTaxi.id + "is allocated");

                
    }
    public static void displayTaxiDetails(){
        for(Taxi taxi : taxies){
            System.out.println();
            System.out.println("Taxi-"+taxi.id+" Total Earnings: Rs."+ taxi.totalEarnings);
            System.out.println();
            System.out.printf("%-10s %-10s %-5s %-5s %-12s %-9s %-6%n","BookingId","customerId","From","to","PickUpTime","DropTime","AmountEarned");
            for(Booking booking : taxi.bookings){
                System.out.println();
                System.out.printf("%-10s %-10s %-5s %-5s %-12s %-9s %-6%n",booking.bookingId,booking.customerId,booking.from,booking.to,booking.pickupTime,booking.dropTime,booking.amount);

            }
        }
    }

        
    
}
