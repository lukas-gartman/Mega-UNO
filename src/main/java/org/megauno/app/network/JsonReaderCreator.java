package org.megauno.app.network;


import java.util.List;

public interface JsonReaderCreator {
    JSONReader createReader(List<Integer> ids);
}
