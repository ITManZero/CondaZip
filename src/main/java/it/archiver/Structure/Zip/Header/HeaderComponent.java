package it.archiver.Structure.Zip.Header;

import it.archiver.Structure.Zip.Operation.DePackData;
import it.archiver.Structure.Zip.Operation.PackData;

public abstract class HeaderComponent<T> implements PackData , DePackData {
        protected int offset , length;
        protected T value;
        public long getOffset() { return offset; }
        public long getLength() { return length; }
        public T getValue() { return value; }
        public void setValue(T val){ value = val; }
        public void setOffset(int off) { offset = off; }
        public void setLength(int len) { length = len; }

}
