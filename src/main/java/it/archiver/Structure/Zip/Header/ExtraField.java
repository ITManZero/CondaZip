package it.archiver.Structure.Zip.Header;
/*

     This SHOULD be used for storage expansion.  If additional
     information needs to be stored within a ZIP file for special
     application or platform needs, it SHOULD be stored here.
     Programs supporting earlier versions of this specification can
     then safely skip the file, and find the next file or header.
     This field will be 0 length in version 1.0.

     Existing extra fields are defined in the section
     Extensible data fields that follows.


    Each header MUST consist of:

       Header ID - 2 bytes
       Data Size - 2 bytes

   Note: all fields stored in Intel low-byte/high-byte order (little endian).

   The Header ID field indicates the type of data that is in
   the following data block.

   Header IDs of 0 thru 31 are reserved for use by PKWARE.
   The remaining IDs can be used by third party vendors for
   proprietary usage.

   4.5.2 The current Header ID mappings defined by PKWARE are:

      0x0001        Zip64 extended information extra field
      0x0007        AV Info
      0x0008        Reserved for extended language encoding data (PFS)
                    (see APPENDIX D)
      0x0009        OS/2
      0x000a        NTFS
      0x000c        OpenVMS
      0x000d        UNIX
      0x000e        Reserved for file stream and fork descriptors
      0x000f        Patch Descriptor
      0x0014        PKCS#7 Store for X.509 Certificates
      0x0015        X.509 Certificate ID and Signature for
                    individual file
      0x0016        X.509 Certificate ID for Central Directory
      0x0017        Strong Encryption Header
      0x0018        Record Management Controls
      0x0019        PKCS#7 Encryption Recipient Certificate List
      0x0020        Reserved for Timestamp record
      0x0021        Policy Decryption Key Record
      0x0022        Smartcrypt Key Provider Record
      0x0023        Smartcrypt Policy Key Data Record
      0x0065        IBM S/390 (Z390), AS/400 (I400) attributes
                    - uncompressed
      0x0066        Reserved for IBM S/390 (Z390), AS/400 (I400)
                    attributes - compressed
      0x4690        POSZIP 4690 (reserved)

 */

import it.archiver.Structure.Zip.Operation.ZipArchiver;

public class ExtraField extends HeaderComponent<Long> {


    public ExtraField(long value , int offset , int length) {
        this.value  = value;
        this.offset = offset;
        this.length = length;

    }

    public Object read(byte[] buffer) {
        if(length != -1) return ZipArchiver.read(buffer, offset, length);
        return null;
    }


    public void write(byte[] buffer) {
        if(value != -1)
            ZipArchiver.write(buffer , offset , length , value);
    }

}
