package it.archiver.Structure.Zip.ZipStructure;

import it.archiver.Structure.Zip.Factories.ZipFactory;
import it.archiver.Structure.Zip.Header.HeaderComponent;
import it.archiver.Structure.Zip.Operation.PackData;

import java.io.IOException;

public class CentralTail implements PackData {
    private int length;
    /*
      End of central directory record:
      size of the central directory   4 bytes
      offset of start of central
      end of central dir signature    4 bytes  (0x06054b50) = 101010256
      number of this disk             2 bytes
      number of the  disk with the
      start of the central directory  2 bytes
      total number of entries in the
      central directory on this disk  2 bytes
      total number of entries in
      the central directory           2 bytes
      directory with respect to
      the starting disk number        4 bytes
      .ZIP file comment length        2 bytes
      .ZIP file comment       (variable size)


      +number of this disk             2 bytes   <=  You only have one disk/file, so set it to 1.

      number of the disk with the
    start of the central directory  2 bytes   <=  You only have one disk/file, so set it to 1.

    total number of entries in the
    central directory on this disk  2 bytes   <=  Set it to the overall number of records.

    offset of start of central
    directory with respect to
    the starting disk number        4 bytes   <=  Set this offset (in bytes) relative to
                the start of your archive

 */
    private HeaderComponent[] headerComponents;

    public CentralTail(int entND ,int entWN ,int offset ,int dic){
        length =0;
        headerComponents = ZipFactory.getTail(entND,entWN,offset,dic);
        for(HeaderComponent hc : headerComponents){
            length += hc.getLength();
        }
    }

    public int getLength() {
        return length;
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        for(HeaderComponent hc : headerComponents){
            hc.write(buffer);
        }
    }
}
