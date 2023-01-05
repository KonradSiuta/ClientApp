package up;

import up.DB.DBConnection;
import up.DB.DBOperation;
import up.server.SecretInfo;
import up.server.UDPClient;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    public static int[] tableInt;
    public static List<Integer> numberList;

    public static void main(String[] args) throws Exception {
//        tableInt = new int[2000];
//        fileList = new ArrayList<>();
        numberList = new ArrayList<>();
//        for(int i =0; i< 2000; i++){
//            tableInt[i] = -1;
//        }

//        up.Client c = new up.Client("localhost", 5501);
//        c.connection();
//        c.sendMessage();
//        c.disconnect();
//        up.FileClient fc = new up.FileClient("localhost", 5501, "pliki");
//        fc.connect();
//        // pobieranie plików z serwera
//        fc.getFileFromServer();
//        // wysyłanie plików do serwera
//        fc.sendFileToServer();
//        fc.disconnect();
//        PackageZip pz = new PackageZip();
//        File[] files = Paths.get("pliki").toFile().listFiles();
////        pz.packingArchive(files, "Archiwum.zip");
//        pz.unpackingArchive(Path.of("pliki_out"), "Archiwum.zip");

//        Thread threadFile = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                File[] fils = Paths.get("pliki").toFile().listFiles();
//                fileList = Arrays.stream(fils).collect(Collectors.toList());
//                for (int i = 0; i< 15; i++){
//                    fils = Paths.get("pliki").toFile().listFiles();
//                    if (fils.length != fileList.size()){
//                        System.out.println("Zmieniła się liczba plików");
//                    }
//                    try {
//                        Thread.sleep(6000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                for (int i = 0; i < 20; i++) {
                    System.out.println("Wartość " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
//        th.start();
//        th.join();
//        TestThread tsR = new TestThread();
//        tsR.startRunnambleFixed(10);
//        tsR.startCallable(10);
//        TikTak tt = new TikTak();
//        StartTikTak t1 = new StartTikTak("Tik", tt);
//        StartTikTak t2 = new StartTikTak("Tak", tt);

        DBConnection connection = new DBConnection();
//        Connection c = connection.connectToSqlite();
//        connection.createTable();n(
//        DBOperation operation = new DBOperation(c);
//        operation.insertPerson("Konrad", "Siuta", 22);
//        operation.selectPerson();
//        operation.updatePerson();
//        operation.deleteRecord();
//        connection.disconnect();

//        Connection c = connection.connectToMySql();
//        DBOperation operation = new DBOperation(c);
//        operation.insertPerson("Adam", "Malinowski", 50);
//        operation.getCountPerson();
//        operation.getPersons(2);
//        operation.getAllPersons();
//        operation.getReversedRecordSet();
//        Savepoint s = connection.getPoint();
//        operation.insertPerson("Adam", "Malinowski", 59);
//        operation.getAllPersons();
//        connection.getRollback(s);
//        c.commit();
//        operation.getAllPersons();
//        System.out.println("Zakończenie wątka main");

//        UDPClient client = new UDPClient();
//        for (int i = 0; i < 15; i++) {
//            client.getMessage("" + i);
//        }
//        client.getMessage("end");

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                UDPClient client = new UDPClient();
                for (int i = 0; i < 15; i++) {
                    client.getMessage("" + i);
                }
            }
        });

//        ExecutorService service = Executors.newFixedThreadPool(5);
//        for (int i = 0; i<5;i++){
//            service.submit(new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    UDPClient client = new UDPClient();
//                    for (int i = 0; i < 15; i++) {
//                        client.getMessage("" + i);
//                    }
//                    client.getMessage("end");
//                }
//            }));
//        }
//
//        service.shutdown();

        BoardGame bg = new BoardGame();
        List<BoardGame> boardGameList = bg.initListGame();

//        Stream<BoardGame> stream = boardGameList.stream();

//        Map<Integer, List<BoardGame>> tempList = boardGameList.stream()
//                .filter(g -> g.name.contains("g"))
//                .filter(g -> g.minPlayers > 1)
//                .filter(g -> g.price < 50)
//                .collect(Collectors.groupingBy(BoardGame::getYear));

        BoardGame firstBoardGame = boardGameList.stream()
                .filter(g -> g.name.contains("g"))
                .filter(g -> g.minPlayers > 1)
                .max(Comparator.comparing(BoardGame::getRating))
                .orElse(new BoardGame());

//        System.out.println(firstBoardGame);

        Supplier<Stream<BoardGame>> stream = () -> boardGameList.stream();


        List<BoardGame> boardGamesSorted = boardGameList.stream()
                .sorted(Comparator.comparing(BoardGame::getYear).reversed())
                .collect(Collectors.toList());

        Predicate<BoardGame> find = g -> g.maxPlayers == 4;

//        stream.get().mapToDouble(BoardGame::getRating).max()

//        if (stream.get().anyMatch(find)) {
//            System.out.println("Tak");
//        } else {
//            System.out.println("Nie");
//        }
//
//        if (stream.get().allMatch(find)) {
//            System.out.println("Tak");
//        } else {
//            System.out.println("Nie");
//        }

        boardGameList.stream().anyMatch(find);

//        System.out.println(tempList);

        List<BoardGame> example = new ArrayList<>();
        List<BoardGame> example1 = new ArrayList<>();

        //zad a,c
        example1 = stream.get()
                .filter(g -> g.year >= 2010)
                .filter(g -> g.year <= 2019)
                .filter(g -> g.name.split(" ").length >= 4)
                .limit(6)
                .toList();

//        System.out.println(example1);

        //zad  b

        example = stream.get()
                .filter(g -> g.rating >= 7.0)
                .filter(g -> g.rating <= 8.5)
                .filter(g -> g.price > 60)
                .sorted(Comparator.comparing(BoardGame::getPrice))
                .collect(Collectors.toList());

//        System.out.println(example);
        //zad d,e

        Map<Double, List<BoardGame>> zadD = boardGameList.stream()
                .sorted(Comparator.comparing(BoardGame::getName))
                .collect(Collectors.groupingBy(BoardGame::getRating));

//        System.out.println(zadD);
        //zad f

//        System.out.println("Średnia: " + stream.get()
//                .filter(g -> g.year >= 2010)
//                .filter(g -> g.year <= 2019)
//                .filter(g -> g.name.split(" ").length >= 4)
//                .limit(6)
//                .mapToDouble(BoardGame::getPrice)
//                .average());

//        System.out.println("Średnia: " + example1.stream()
//                .mapToDouble(BoardGame::getPrice)
//                .average().getAsDouble());
//
//        System.out.println("Suma: " + example1.stream()
//                .mapToDouble(BoardGame::getPrice)
//                .sum());


        //zad g

//        System.out.println(stream.get()
//                .filter(g -> g.year == 2015)
//                .filter(g -> g.minPlayers >= 2)
//                .filter(g -> g.maxPlayers <= 4)
//                .findFirst()
//                .orElse(new BoardGame())
//        );

        //zad h

//        System.out.println(stream.get()
//                .anyMatch(g -> g.rating == 5.6)
//        );

//        bg.addYearToGame(boardGameList ,4);

//        bg.gameListSorted(boardGameList);

//        SecretInfo si = new SecretInfo();
//        byte[] key = "1234567890qwerty".getBytes();
//        byte[] encMsg = si.encryptMessage(key, "Wiadmość do zaszyfrowania".getBytes(StandardCharsets.UTF_8));

//        System.out.println(new String(encMsg));

//        String decMsg = si.decryptMessage(key, encMsg);
//        System.out.println(decMsg);


        WebBot wb = new WebBot();

//        StringBuilder stringBuilder = wb.getWebPage("https://www.up.krakow.pl");
//        System.out.println(stringBuilder);

//        wb.startSearch("https://www.up.krakow.pl");

        wb.findRate("http://api.nbp.pl/api/exchangerates/tables/A?format=xml");
    }

}
