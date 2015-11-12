package ebooksharing;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-08T05:45:17")
@StaticMetamodel(Pendingbook.class)
public class Pendingbook_ { 

    public static volatile SingularAttribute<Pendingbook, Serializable> cover;
    public static volatile SingularAttribute<Pendingbook, String> summary;
    public static volatile SingularAttribute<Pendingbook, Serializable> bookfile;
    public static volatile SingularAttribute<Pendingbook, String> uploader;
    public static volatile SingularAttribute<Pendingbook, String> author;
    public static volatile SingularAttribute<Pendingbook, Integer> pbid;
    public static volatile SingularAttribute<Pendingbook, String> bookname;
    public static volatile SingularAttribute<Pendingbook, Short> grantedPoints;
    public static volatile SingularAttribute<Pendingbook, Short> requestPoints;

}