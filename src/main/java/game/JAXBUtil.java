package game;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.OutputStream;

public class JAXBUtil {

    /**
     * XML formátumúra ír objektumokat.
     *
     * @param o  az átírandó objektum
     * @param os az {@link OutputStream} ahová írunk
     * @throws JAXBException ha bármi probléma van a folyamat során
     */
    public static void toXML(Object o, OutputStream os) throws JAXBException {
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(o, os);
        } catch (JAXBException e) {
            throw e;
        }
    }

    /**
     * XML-ből olvas be objektumba
     *
     * @param clazz az objektum osztálya
     * @param is    az {@link InputStream} ahonnan olvasunk
     * @return a célobjektum
     * @throws JAXBException ha bármi probléma van a beolvasás során
     */
    public static <T> T fromXML(Class<T> clazz, InputStream is) throws JAXBException {
        try {//todo make it work
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            throw e;
        }
    }

}
