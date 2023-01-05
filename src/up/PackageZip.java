package up;

import java.io.*;
import java.nio.file.Path;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class PackageZip {

    public void packingArchive(File[] files, String zipName){
        if (files.length > 0){
            try {
                ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipName));
                zipOut.setLevel(Deflater.BEST_COMPRESSION);
                zipOut.setMethod(ZipEntry.DEFLATED);
                for (File f : files){
                    ZipEntry zipE = new ZipEntry(f.getName());
                    zipOut.putNextEntry(zipE);
                    FileInputStream fIn = new FileInputStream(f);
//                    zipOut.write(fIn.readAllBytes());
                    // alternatywne rozwiązanie dla jav-y w wersji 1.8
                    int len;
                    byte[] buff = new byte[2048];
                    while (( len = fIn.read(buff)) >= 0){
                        zipOut.write(buff,0, len);
                    }
                    fIn.close();
                    zipOut.closeEntry();
                }
                zipOut.flush();
                zipOut.close();
                System.out.println("Zapisano pliki do archiwum ZIP");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("brak plików do spakowania");
        }
    }

    public void unpackingArchive(Path dLocation, String zipName){
        try {
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipName));
            ZipEntry zipE;
            while (( zipE = zipIn.getNextEntry()) != null){
                FileOutputStream fOut = new FileOutputStream(
                        new File(dLocation.toString(), zipE.getName()));
//                fOut.write(zipIn.readAllBytes());
                // alternatywne rozwiązanie dla jav-y w wersji 1.8
                int len;
                byte[] buff = new byte[2048];
                while (( len = zipIn.read(buff)) >= 0){
                    fOut.write(buff,0, len);
                }
                fOut.flush();
                fOut.close();
                System.out.println("Rozpakowany plik " + zipE.getName() + " " + zipE.getSize());
                zipIn.closeEntry();
            }
            zipIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
