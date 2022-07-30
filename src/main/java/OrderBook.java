


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class OrderBook implements BookCommands {
    private static OrderBook obj = new OrderBook();
    private static OrderBook[] new_objects = new OrderBook[200];
    private static List<OrderBook> list;
    private String[] string;
    private Character type;
    private int size;
    private int price;
    private static String output = "";
    private static String result = "";


    public OrderBook(Character type, int price, int size) {
        this.type = type;
        this.price = price;
        this.size = size;
    }
    public OrderBook(){}

    public int getPrice() {
        return price;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getType() {
        return type;
    }

    public static void main(String[] args) throws IOException {

        /* The order book */
        list = new LinkedList<>();
        list.sort(Comparator.comparing(OrderBook::getPrice));

        File text = new File("input.txt");

        StringBuilder s = new StringBuilder();

        Scanner in = null;
        try {
            in = new Scanner(text);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (in.hasNext())
            s.append(in.nextLine()).append(" \r\n");
        /* break text into lines */
        String[] res = s.toString().split(" \r\n");



        for (int i = 0; i < res.length; i++) {
            if (res[i].charAt(0) == 'q') {
                obj.proceedQuery(res[i]);
            } else if (res[i].charAt(0) == 'o') {
                obj.proceedOrder(res[i]);
            } else if (res[i].charAt(0) == 'u') {
                obj.proceedUpdate(res[i]);
            } else System.out.println("input error");
        }
        in.close();
        writeFile(output);

    }

    @Override
    public void proceedOrder(String str) {
        /* break line into words */
        string = str.split(",");
        int num = Integer.parseInt(string[2]);
        if (string[1].equals("sell")) {
            obj.removeBestBid(num);
        } else if (string[1].equals("buy")) {
            obj.removeBestAsk(num);
        }
    }

    @Override
    public void proceedUpdate(String str) {
        string = str.split(",");
        price = Integer.parseInt(string[1]);
        size = Integer.parseInt(string[2]);
        if (string[3].equals("bid")) {
            type = 'B';
            for (int i = 0; i < 100; i++) {
                new_objects[i] = new OrderBook(type, price, size);
                list.add(new_objects[i]);
                break;
            }
        } else if (string[3].equals("ask")) {
            type = 'A';
            for (int i = 0; i < 100; i++) {
                new_objects[i] = new OrderBook(type, price, size);
                list.add(new_objects[i]);
                break;
            }
        }
    }


    @Override
    public void proceedQuery(String str) throws IOException {
        //String result = "";

        string = str.split(",");

        if (string[1].equals("best_bid")) {
            obj.getBestBid();
        } else if (string[1].equals("best_ask")) {
            obj.getBestAsk();
        }
        /* for expression q,size,<price> */
        else if (string[1].equals("size")) {
            int num = Integer.parseInt(string[2]);

            for (OrderBook orderBook : list) {
                if (num == orderBook.getPrice()) {
                    System.out.print((orderBook.getSize() + " \r\n"));
                    result = orderBook.getSize() + " \r\n";
                    output += result;
                    break;
                }
            }
        }
    }

    public void getBestBid() throws IOException {
        //String result = "";
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getType() == 'B') {
                System.out.print((list.get(i).getPrice() + "," + list.get(i).getSize() + " \r\n"));
                result = list.get(i).getPrice() + "," + list.get(i).getSize() + " \r\n";
                output += result;

                break;
            }
        }
    }

    public void removeBestBid(int n) {
        size = size - n;

        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getType() == 'B') {
                list.get(i).setSize(size);
                break;
            }
        }
    }

    public void getBestAsk() throws IOException {
        //String result = "";
        for (OrderBook orderBook : list) {
            if (orderBook.getType() == 'A') {
                System.out.print((orderBook.getPrice() + "," + orderBook.getSize() + "\r\n"));
                result = orderBook.getPrice() + "," + orderBook.getSize() + "\r\n";
                output += result;
                break;
            }
        }
    }

    public void removeBestAsk(int n) {
        size = size - n;

        for (OrderBook orderBook : list) {
            if (orderBook.getType() == 'A') {
                orderBook.setSize(size);
                break;
            }
        }
    }

    public static void writeFile(String str) {
        try {
            File file = new File("output.txt");
            if (file.createNewFile())
                System.out.println("file created");
            else
                System.out.println("file already exists");
            PrintWriter pw = new PrintWriter(file);
            pw.println(output);
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
