package org.bushkovsky;

import org.bushkovsky.Reader.Reader;
import org.bushkovsky.services.Service;
import org.bushkovsky.writers.Writer;


public class Main {

    public static void main(String[] args) {
        Service service = new Service();
        Reader reader = new Reader();
        ResultMaker maker = new ResultMaker();
        Writer writer = new Writer();

        reader.read(args[0], service);
        writer.write(args[0], maker.getRank(service));
    }
}