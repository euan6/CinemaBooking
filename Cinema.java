package CinemaRoom;
import java.util.Scanner;

public class Cinema {

	private static Scanner scan = new Scanner(System.in);
	private static char[][] cinemaHall;
	private static int numRows, numSeats, totalNumSeats, seatsTaken, totalIncome, numBookings;

	public static void main(String[] args) {
		startCinemaBooking();
	}

	private static void startCinemaBooking() {
		//let user create the cinema then continue to display the menu until user chooses to exit
		createCinema();
		int command;
		do {
			showMenu();
			while (!scan.hasNextInt()) { 
				System.out.println("Error! Please enter either 1, 2, 3: ");
				showMenu();
				scan.next(); 
			}
			command = scan.nextInt();
			switch (command) {
			case 1:
			//show seats
				printCinemaHall();
				break;
			case 2:
			//books a ticket
				bookTicket();
				break;
			case 3: 
			//check the cinema statistics
				checkCinemaAvailability();
				break;
			case 4:
			//delete a booking
				deleteBooking();
				break;
			case 5:
			//upgrade a seat
				upgradeSeat();
				break;
			case 6:
			//check if a ticket has been bought or not for exit message
				if (numBookings == 0) {
					System.out.println("Thank you for browsing the cinema!");
				} else {
					System.out.println("Thank you for booking a ticket!");
				}
				break;
			default:
			//error message if incorrect input is given
				System.out.println("Error! Please enter either 1, 2, 3: ");
				break;
			}
		} while (command != 6);
	}

	private static void showMenu() {
		//prints the menu options
		System.out.println("\n--- Cinema Menu ---");
		System.out.println("1. Show the seats");
		System.out.println("2. Buy a ticket");
		System.out.println("3. Check availability");
		System.out.println("4. Delete ticket");
		System.out.println("5. Upgrade seat");
		System.out.println("6. Exit");
	}

	private static void createCinema() {
		//creates a cinema of n*n size where n is a number 1 - 9
		System.out.print("Enter the number of rows: ");
		isNumeric();
		numRows = scan.nextInt();
		while (numRows < 1 || numRows > 9) {
			System.out.print("Error! number must be between (1 - 9): ");
			isNumeric();
			numRows = scan.nextInt();
		}
		System.out.print("Enter the number of seats in each row: ");
		isNumeric();
		numSeats = scan.nextInt();
		while (numSeats < 1 || numSeats > 9) {
			System.out.print("Error! number must be between (1 - 9): ");
			isNumeric();
			numSeats = scan.nextInt();
		}
		cinemaHall = new char[numRows][numSeats];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numSeats; j++) {
				cinemaHall[i][j] = 'A';
			}
		}
		totalNumSeats = cinemaHall.length * cinemaHall[0].length;
	}

	private static void isNumeric() {
		//checks if the user has entered a numeric value
		while (!scan.hasNextInt()) { //check if input is an integer
			System.out.print("Error! Please enter a numeric value: ");
			scan.next(); //consume the invalid input
		}
	}

	private static void printCinemaHall() {
		//print the size of the cinema and the availability of seats
		System.out.println("Cinema:");
		System.out.print(" ");
		for (int i = 1; i < cinemaHall[0].length + 1; i++) {
			System.out.print(" " + i);
		}
		System.out.println();
		for (int i = 0; i < cinemaHall.length; i++) {
			System.out.print(i + 1);
			for (int j = 0; j < cinemaHall[0].length; j++) {
				System.out.print(" " + cinemaHall[i][j]);
			}
			System.out.println();
		}
	}

	private static void bookTicket() {
		//user chooses which seat they would like to book
		System.out.println("Enter a row number: ");
		isNumeric();
		int seatRow = scan.nextInt();
		while (seatRow < 1 || seatRow > numRows) {
			System.out.print("Error! please enter a valid row number: ");
			isNumeric();
			seatRow = scan.nextInt();
		}
		System.out.println("Enter a seat number in that row: ");
		isNumeric();
		int seatNum = scan.nextInt();
		while (seatNum < 1 || seatNum > numSeats) {
			System.out.print("Error! please enter a valid seat number: ");
			isNumeric();
			seatNum = scan.nextInt();
		}
		int ticketPrice = calcTicketPrice(seatRow);
		checkSeatAvailable(seatRow, seatNum, ticketPrice);
		totalIncome += ticketPrice;
	}

	private static void deleteBooking() {
		//user chooses which booking they would like to delete
		System.out.println("Enter your row number: ");
		isNumeric();
		int seatRow = scan.nextInt();
		while (seatRow < 1 || seatRow > numRows) {
			System.out.print("Error! please enter a valid row number: ");
			isNumeric();
			seatRow = scan.nextInt();
		}
		System.out.println("Enter your seat number in that row: ");
		isNumeric();
		int seatNum = scan.nextInt();
		while (seatNum < 1 || seatNum > numSeats) {
			System.out.print("Error! please enter a valid seat number: ");
			isNumeric();
			seatNum = scan.nextInt();
		}
		if (cinemaHall[seatRow - 1][seatNum - 1] == 'A') {
			System.out.println("There is currently no booking for that seat!");
			return;
		} else if (cinemaHall[seatRow - 1][seatNum - 1] == 'T') {
			int ticketPrice = calcTicketPrice(seatRow);
			totalIncome -= ticketPrice;
			seatsTaken--;
			numBookings--;
			cinemaHall[seatRow - 1][seatNum - 1] = 'A';
			System.out.println("Booking successfully deleted!");
		} else if (cinemaHall[seatRow - 1][seatNum - 1] == 'P') {
			int ticketPrice = calcTicketPrice(seatRow);
			totalIncome -= (ticketPrice + 5);
			seatsTaken--;
			numBookings--;
			cinemaHall[seatRow - 1][seatNum - 1] = 'A';
			System.out.println("Booking successfully deleted!");
		}
	}

	private static void upgradeSeat() {
		//user chooses which seat they would like to upgrade
		System.out.println("Enter your row number: ");
		isNumeric();
		int seatRow = scan.nextInt();
		while (seatRow < 1 || seatRow > numRows) {
			System.out.print("Error! please enter a valid row number: ");
			isNumeric();
			seatRow = scan.nextInt();
		}
		System.out.println("Enter your seat number in that row: ");
		isNumeric();
		int seatNum = scan.nextInt();
		while (seatNum < 1 || seatNum > numSeats) {
			System.out.print("Error! please enter a valid seat number: ");
			isNumeric();
			seatNum = scan.nextInt();
		}
		if (cinemaHall[seatRow - 1][seatNum - 1] == 'A') {
			System.out.println("There is currently no booking for that seat!");
			return;
		} else {
			totalIncome += 5;
			cinemaHall[seatRow - 1][seatNum - 1] = 'P';
			System.out.println("Booking successfully upgraded!");
			System.out.println("Upgrade price: $5");
		}
	}

	private static int calcTicketPrice(int seatRow) {
		//returns the cost of the ticket choosen
		if (totalNumSeats <= 60 || seatRow <= numRows / 2) {
			return 10;
		} else {
			return 8;
		}
	}

	private static void checkCinemaAvailability() {
		//prints the amount of seats taken and available, and shows how much money has been made
		System.out.println("Total number of seats in the cinema: " + totalNumSeats);		
		System.out.println("Total number of seats available: " + (totalNumSeats - seatsTaken));
		System.out.println("Total income made so far: $" + totalIncome);
	}

	private static void checkSeatAvailable(int seatRow, int seatNum, int priceTicket) {
		//checks if the seat that is trying to be booked is already taken
		if (cinemaHall[seatRow - 1][seatNum - 1] == 'T') {
			System.out.println("Seat is already taken!");
		} else {
			cinemaHall[seatRow - 1][seatNum - 1] = 'T';
			System.out.println("Ticket price: $" + priceTicket);
			seatsTaken++;
			numBookings++;
		}
	}
}