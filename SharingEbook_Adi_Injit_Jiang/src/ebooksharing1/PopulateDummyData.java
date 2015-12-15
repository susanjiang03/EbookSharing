/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;

/**
 *
 * @author lingshanjiang
 */
public class PopulateDummyData {

    public String getClassName() {
        String className = this.getClass().getSimpleName();
        return className;
    }

    public static void main(String args[]) throws SQLException, FileNotFoundException {

        String insert_userinfo = "INSERT INTO userInfo (username,password,email,firstname,lastname,point_balance,is_SU) \n"
                + "VALUES\n"
                + " ('susan','susan','ljiang001@citymail.cuny.edu','Lingshan','Jiang',1000,True),\n"
                + " ('injit','injit','dictum.eleifend@nisia.net','Adria','Barlow',197,True), \n"
                + "('adi','adi','interdum.feugiat.Sed@duiCum.net','Gemma','Tyson',275,True), "    
                + " ('user1','user1','odio@lobortisauguescelerisque.com','Bo','Washington',26,True),\n"
                + " ('user2','user2','dictum.eleifend@nisia.net','Adria','Barlow',197,False), \n"
                + "('user3','user3','interdum.feugiat.Sed@duiCum.net','Gemma','Tyson',275,False), "
                + "('user4','user4','mi.felis.adipiscing@Pellentesque.net','Bree','Stephenson',263,False), \n"
                + "('user5','user5','dis.parturient.montes@pellentesque.net','Tashya','Lindsay',756,False), \n"
                + "('user6','user6','lectus.pede@ligulaNullamenim.org','Penelope','Gallagher',155,False), \n"
                + "('user7','user7','Proin.eget.odio@auctorodio.com','Kasper','Sexton',140,False), \n"
                + "('user8','user8','dui@euenimEtiam.co.uk','Clark','Logan',502,False), \n"
                + "('user9','user9','cursus@enimsit.co.uk','Petra','Mcdaniel',410,False), \n"
                + "('user10','user10','dictum.Proin@sagittis.edu','Nerea','Acosta',561,False),\n"
                + " ('user11','vitae','sit.amet@aliquetnecimperdiet.org','Tallulah','Oliver',523,False), \n"
                + "('user12','felis','aliquet.libero.Integer@consequatpurusMaecenas.ca','David','Kennedy',583,False), \n"
                + "('user13','dapibus','morbi.tristique@hendreritDonecporttitor.ca','Piper','Buckner',274,False), \n"
                + "('user14','non,','bibendum.fermentum.metus@fringilla.ca','MacKensie','Frank',529,False), \n"
                + "('user15','nunc.','erat@sem.edu','Mira','Rodgers',530,False),\n"
                + " ('user16','arcu.','dui@loremutaliquam.org','Kyla','Decker',244,False), \n"
                + "('user17','semper','dictum.cursus@Suspendisseeleifend.org','Fredericka','Barker',176,False), \n"
                + "('user18','enim.','diam.dictum.sapien@eratneque.ca','Asher','Shelton',250,False),\n"
                + " ('user19','sollicitudin','ullamcorper.nisl@aliquetodio.net','Erin','Slater',523,False), \n"
                + "('user20','faucibus','neque@quislectus.ca','Lynn','Blanchard',366,False)\n";
   
               

        String insert_bookinfo = "INSERT INTO BOOKINFO(cover,bookfile,uploader,bookname,author,summary,last_date_read,reading_total_duration,reading_counts,rating,rating_counts,reward_points,reading_points,category) \n"
                + "VALUES(?,?,'user1','magnis dis parturient montes, nascetur ridiculus mus. Proin vel arcu','Sebastian Oneil','ultrices. Duis volutpat nunc sit amet metus. Aliquam erat volutpat. Nulla facilisis. Suspendisse commodo tincidunt nibh. Phasellus nulla. Integer vulputate, risus a ultricies adipiscing, enim mi tempor lorem, eget mollis lectus pede et risus. Quisque libero lacus, varius et, euismod et, commodo at, libero. Morbi accumsan laoreet ipsum. Curabitur consequat, lectus sit amet luctus','07/13/2015',5592,97,9,294,838,88,'Adventure'),\n"
                + "(?,?,'user2','lacus. Aliquam rutrum lorem ac','Cassandra Alford','Fusce mi lorem, vehicula et, rutrum eu, ultrices sit amet, risus. Donec nibh enim, gravida sit amet, dapibus id, blandit at, nisi. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin vel nisl. Quisque fringilla euismod enim. Etiam gravida molestie arcu. Sed eu nibh vulputate mauris sagittis placerat. Cras dictum ultricies ligula. Nullam','12/22/2014',8884,290,4,100,1299,2,'Adventure'),\n"
                + "(?,?,'user3','Nam interdum enim non nisi.','Plato Christian','et, magna. Praesent interdum ligula eu enim. Etiam imperdiet dictum magna. Ut tincidunt orci quis lectus. Nullam suscipit, est ac facilisis facilisis, magna tellus faucibus leo, in lobortis tellus justo sit amet nulla. Donec non justo. Proin non massa non ante bibendum ullamcorper. Duis cursus, diam at pretium aliquet, metus urna convallis erat, eget tincidunt dui augue eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus id, libero. Donec consectetuer mauris id sapien. Cras dolor dolor, tempus non, lacinia at, iaculis quis, pede. Praesent eu dui. Cum sociis natoque penatibus et magnis dis mus. Aenean','04/28/2016',9507,100,5,89,1882,1,'Adventure'),\n"
                + "(?,?,'user4','mollis.','Yuli Walker','elit, pellentesque a, facilisis non, bibendum sed, est. Nunc laoreet lectus quis massa. Mauris vestibulum, neque sed dictum eleifend, nunc risus varius orci, in consequat enim diam vel arcu. Curabitur ut odio vel est tempor bibendum. Donec felis orci, adipiscing non, luctus sit amet, faucibus ut, nulla. Cras eu tellus eu augue porttitor interdum. Sed auctor odio a purus.','08/09/2015',3912,259,1,93,1672,7,'Adventure'),\n"
                + "(?,?,'user5','tellus eu','Kasper Jarvis','nibh. Donec est mauris, rhoncus id, mollis nec, cursus a, enim. Suspendisse aliquet, sem ut cursus luctus, ipsum leo elementum sem, vitae aliquam eros turpis non enim. Mauris quis turpis vitae purus gravida sagittis. Duis gravida. Praesent eu nulla at sem molestie sodales. Mauris blandit enim consequat purus. Maecenas libero est, congue a, aliquet vel, vulputate eu, odio. Phasellus at augue id ante dictum cursus. Nunc mauris elit, dictum eu, eleifend nec, malesuada ut, sem. Nulla interdum. Curabitur dictum. Phasellus','11/27/2015',3710,242,10,223,1332,1,'Adventure'),\n"
                + "(?,?,'user6','consectetuer adipiscing elit. Curabitur sed tortor. Integer','Nina Norton','ipsum. Suspendisse non leo. Vivamus nibh dolor, nonummy ac, feugiat non, lobortis quis, pede. Suspendisse dui. Fusce diam nunc, ullamcorper eu, euismod ac, fermentum vel, mauris. Integer sem elit, pharetra ut, pharetra sed, hendrerit a, arcu. Sed et libero. Proin mi. Aliquam gravida mauris ut mi. Duis risus odio, auctor vitae, aliquet nec, imperdiet nec, leo. Morbi neque tellus, imperdiet non, vestibulum nec, euismod in, dolor. Fusce feugiat. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam auctor, velit eget laoreet posuere,','12/21/2015',8781,134,8,127,1084,10,'Adventure'),\n"
                + "(?,?,'user7','purus ac tellus. Suspendisse sed dolor. Fusce','Elmo Pacheco','lacus vestibulum lorem, sit amet ultricies sem magna nec quam. Curabitur vel lectus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec dignissim magna a tortor. Nunc commodo auctor velit. Aliquam nisl. Nulla eu neque pellentesque massa lobortis ultrices. Vivamus rhoncus. Donec est. Nunc ullamcorper, velit in aliquet lobortis, nisi nibh lacinia orci, consectetuer euismod est arcu ac orci. Ut semper pretium neque. Morbi quis urna. Nunc quis arcu vel quam dignissim pharetra. Nam ac nulla. In tincidunt congue turpis. In condimentum. Donec at arcu.','10/24/2015',7327,52,4,119,1619,6,'Adventure'),\n"
                + "(?,?,'user8','et libero. Proin mi. Aliquam gravida mauris ut mi.','Lila Booker','Maecenas iaculis aliquet diam. Sed diam lorem, auctor quis, tristique ac, eleifend vitae, erat. Vivamus nisi. Mauris nulla. Integer urna. Vivamus molestie dapibus ligula. Aliquam erat volutpat. Nulla dignissim. Maecenas ornare egestas ligula. Nullam feugiat placerat velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl. Maecenas malesuada fringilla est. Mauris eu turpis. Nulla aliquet. Proin velit. Sed malesuada augue ut lacus. Nulla tincidunt, neque vitae semper egestas, urna justo faucibus lectus, a sollicitudin orci sem eget massa. Suspendisse eleifend. Cras sed leo. Cras vehicula','09/28/2016',9247,160,7,99,1343,6,'Adventure'),\n"
                + "(?,?,'user9','quis diam. Pellentesque habitant morbi tristique senectus et','Brady Gibbs','Praesent eu dui. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean eget magna. Suspendisse tristique neque venenatis lacus. Etiam bibendum fermentum metus. Aenean sed pede nec ante blandit viverra. Donec tempus, lorem fringilla ornare placerat, orci lacus vestibulum lorem, sit amet ultricies sem magna nec quam. Curabitur vel lectus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec dignissim magna a tortor. Nunc commodo auctor velit. Aliquam nisl. Nulla eu neque pellentesque massa lobortis ultrices. Vivamus rhoncus. Donec est. Nunc ullamcorper, velit in aliquet lobortis, nisi nibh','12/31/2014',2499,37,10,256,469,1,'Adventure'),\n"
                + "(?,?,'user10','nunc interdum feugiat. Sed','Alden Stanton','adipiscing ligula. Aenean gravida nunc sed pede. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin vel arcu eu odio tristique pharetra. Quisque ac libero nec ligula consectetuer rhoncus. Nullam velit dui, semper et, lacinia vitae, sodales at, velit. Pellentesque ultricies dignissim lacus. Aliquam rutrum lorem ac risus. Morbi metus. Vivamus euismod urna. Nullam lobortis quam a felis ullamcorper viverra. Maecenas iaculis aliquet diam. Sed diam lorem, auctor quis, tristique ac, eleifend vitae, erat. Vivamus nisi. Mauris nulla. Integer urna. Vivamus molestie dapibus ligula. Aliquam erat volutpat. Nulla dignissim. Maecenas ornare egestas','11/16/2015',1131,169,8,291,878,5,'Biography'),\n"
                + "(?,?,'user11','lorem, sit amet ultricies sem magna nec quam. Curabitur','Blair Briggs','neque. Sed eget lacus. Mauris non dui nec urna suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus ornare. Fusce mollis. Duis sit amet diam eu dolor egestas rhoncus. Proin nisl sem, consequat nec, mollis vitae, posuere at, velit. Cras lorem lorem, luctus ut, pellentesque eget, dictum placerat, augue. Sed molestie.','07/20/2016',7335,180,5,157,698,4,'Biography'),\n"
                + "(?,?,'user12','Sed','Rachel Bauer','Integer aliquam adipiscing lacus. Ut nec urna et arcu imperdiet ullamcorper. Duis at lacus. Quisque purus sapien, gravida non, sollicitudin a, malesuada id, erat. Etiam vestibulum massa rutrum magna. Cras convallis convallis dolor. Quisque tincidunt pede ac urna. Ut tincidunt vehicula risus. Nulla eget metus eu erat semper rutrum. Fusce dolor quam, elementum at, egestas a, scelerisque sed, sapien. Nunc pulvinar arcu et pede. Nunc sed orci lobortis augue scelerisque mollis. Phasellus libero mauris, aliquam eu, accumsan sed, facilisis vitae, orci. Phasellus dapibus quam quis diam. Pellentesque habitant morbi tristique senectus et','07/29/2016',7974,179,4,12,827,1,'Biography'),\n"
                + "(?,?,'user13','ornare sagittis felis. Donec tempor, est','Glenna Calderon','Proin non massa non ante bibendum ullamcorper. Duis cursus, diam at pretium aliquet, metus urna convallis erat, eget tincidunt dui augue eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus id, libero. Donec consectetuer mauris id sapien. Cras dolor dolor, tempus non, lacinia at, iaculis quis, pede. Praesent eu dui. Cum sociis natoque','07/14/2016',7646,238,7,110,1233,1,'Biography'),\n"
                + "(?,?,'user14','Nullam','Timon Curry','tortor at risus. Nunc ac sem ut dolor dapibus gravida. Aliquam tincidunt, nunc ac mattis ornare, lectus ante dictum mi, ac mattis velit justo nec ante. Maecenas mi felis, adipiscing fringilla, porttitor vulputate, posuere vulputate, lacus. Cras interdum. Nunc sollicitudin commodo ipsum. Suspendisse non leo. Vivamus nibh dolor, nonummy ac, feugiat non, lobortis quis, pede. Suspendisse dui. Fusce diam nunc, ullamco vel, mauris. Integer sem elit, pharetra ut, pharetra sed, hendrerit a, arcu. Sed et libero. Proin mi. Aliquam gravida mauris ut mi. Duis risus odio, auctor vitae, aliquet nec, imperdiet nec, leo.','12/17/2014',2269,99,7,242,616,4,'Biography'),\n"
                + "(?,?,'user15','semper, dui','Wynter Welch','sem mollis dui, in sodales elit erat vitae risus. Duis a mi fringilla mi lacinia mattis. Integer eu lacus. Quisque imperdiet, erat nonummy ultricies ornare, elit elit fermentum risus, at fringilla purus mauris a nunc. In at pede. Cras vulputate velit eu sem. Pellentesque ut ipsum ac mi eleifend egestas. Sed pharetra, felis eget varius ultrices, mauris ipsum porta elit, a feugiat tellus lorem eu metus. In lorem. Donec elementum, lorem ut aliquam','01/20/2016',3467,246,3,13,487,6,'Biography'),\n"
                + "(?,?,'user16','cursus non, egestas a,','Leo Jensen','molestie in, tempus eu, ligula. Aenean euismod mauris eu elit. Nulla facilisi. Sed neque. Sed eget lacus. Mauris non dui nec urna suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus ornare. Fusce mollis. Duis sit amet diam eu dolor','07/26/2016',6824,232,3,298,1371,14,'Biography'),\n"
                + "(?,?,'user17','felis','Benedict Albert','ornare egestas ligula. Nullam feugiat placerat velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl. Maecenas malesuada fringilla est. Mauris eu turpis. Nulla aliquet. Proin velit. Sed malesuada augue ut lacus. Nulla tincidunt, neque vitae semper egestas, urna justo faucibus lectus, a sollicitudin orci sem eget massa. Suspendisse eleifend. Cras sed','03/29/2016',4957,65,8,57,612,33,'Biography'),\n"
                + "(?,?,'user18','Phasellus dolor elit, pellentesque a, facilisis non, bibendum sed, est.','MacKenzie Beach','tincidunt pede ac urna. Ut tincidunt vehicula risus. Nulla eget metus eu erat semper rutrum. Fusce dolor quam, elementum at, egestas a, scelerisque sed, sapien. Nunc pulvinar arcu et pede. Nunc sed orci lobortis augue scelerisque mollis. Phasellus libero mauris, aliquam eu, accumsan sed, facilisis vitae, orci. Phasellus dapibus quam quis diam. Pellentesque habtus. In nec orci. Donec nibh.','12/22/2014',7990,131,5,190,1970,4,'Biography'),\n"
                + "(?,?,'user19','amet ultricies sem magna nec quam. Curabitur vel lectus.','Jael Keith','auctor odio a purus. Duis elementum, dui quis accumsan convallis, ante lectus convallis est, vitae sodales nisi magna sed dui. Fusce aliquam, enim nec tempus scelerisque, lorem ipsum sodales purus, in molestie tortor nibh sit amet orci. Ut sagittis lobortis mauris. Suspendisse aliquet molestie tellus. Aenean egestas hendrerit neque. In ornare sagittis felis. Donec tempor, est ac mattis semper, dui lectus rutrum urna, nec luctus felis purus ac tellus. Suspendisse sed dolor. Fusce mi lorem, vehicula et, rutrum eu, ultrices sit amet,','06/27/2015',3292,235,6,264,1380,90,'Economics'),\n"
                + "(?,?,'user20','et magnis dis parturient montes, nascetur ridiculus mus. Aenean eget','Melanie Merrill','blandit mattis. Cras eget nisi dictum augue malesuada malesuada. Integer id magna et ipsum cursus vestibulum. Mauris magna. Duis dignissim tempor arcu. Vestibulum ut eros non enim commodo hendrerit. Donec porttitor tellus non magna. Nam ligula elit, pretium et, rutrum non, hendrerit id, ante. Nunc mauris sapien, cursus in, hendrerit consectetuer, cursus et, magna. Praesent interdum ligula eu enim. Etiam imperdiet dictum magna. Ut tincidunt orci quis lectus. Nullam suscipit,','08/19/2015',897,280,7,69,795,68,'Economics')\n";
                
        
        String insert_pendingbook = "INSERT INTO pendingbook(cover,bookfile,uploader,bookname,author,summary,request_points,category) "
                + "VALUES\n"
                + "(?,?,'user5','nisl.','Shoshana V. Meyer','diam luctus lobortis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Mauris ut quam vel sapien imperdiet ornare. In faucibus. Morbi vehicula. Pellentesque tincidunt tempus risus. Donec egestas. Duis ac arcu. Nunc mauris. Morbi non sapien molestie orci tincidunt adipiscing. Mauris molestie pharetra nibh. Aliquam ornare, libero at auctor ullamcorper, nisl arcu iaculis enim, sit amet ornare lectus justo eu arcu. Morbi sit amet massa. Quisque porttitor',404,'Adventure'),\n"
                + "(?,?,'user8','neque. In oc mattis','Dale Howard','non nisi. Aenean eget metus. In nec orci. Donec nibh. Quisque nonummy ipsum non arcu. Vivamus sit amet risus. Donec egestas. Aliquam nec enim. Nunc ut erat. Sed nunc est, mollis non, cursus non, egestas a, dui. Cras pellentesque. Sed dictum. Proin eget odio. Aliquam vulputate ullamcorper magna. Sed eu eros. Nam consequat',957,'Adventure'),\n"
                + "(?,?,'user11','feugiat','Clark Bryant','commodo at, libero. Morbi accumsan laoreet ipsum. Curabitur consequat, lectus sit amet luctus vulputate, nisi sem semper erat, in consectetuer ipsum nunc id enim. Curabitur massa. Vestibulum accumsan neque et nunc. Quisque ornare tortor at risus. Nunc ac sem ut dolor dapibus gravida. Aliquam tincidunt, nunc ac mattis ornare, lectus ante dictum mi, ac mattis velit justo nec ante. Maecenas mi felis, adipiscing',101,'Adventure'),\n"
                + "(?,?,'user14','vel arcu eu odio tristique pharetra. Quisque ac','Ava Q. Weaver','quis diam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce aliquet magna a neque. Nullam ut nisi a odio semper cursus. Integer mollis. Integer tincidunt aliquam arcu. Aliquam ultrices iaculis odio. Nam interdum enim non nisi. Aenean eget metus. In nec orci. Donec nibh. Quisque nonummy ipsum non arcu. Vivamus sit amet risus. Donec egestas. Aliquam nec enim. Nunc ut erat. Sed nunc est, mollis non, cursus non, egestas',634,'Adventure'),\n"
                + "(?,?,'user17','du. Sed dictum. Proin eget odio. Aliquam vulputate','Brett Mullen','egestas blandit. Nam nulla magna, malesuada vel, convallis in, cursus et, eros. Proin ultrices. Duis volutpat nunc sit amet metus. Aliquam erat volutpat. Nulla facilisis. Suspendisse commodo tincidunt nibh. Phasellus nulla. Integer vulputate, risus a ultricies adipiscing, enim mi tempor lorem, eget mollis lectus pede et risus. Quisque libero lacus, varius et, euismod et, commodo at, libero. Morbi accumsan laoreet ipsum. Curabitur consequat, lectus sit amet luctus vulputate, nisi sem semper',29,'Adventure'),\n"
                + "(?,?,'user2','adit eget laoreet posuere, enim nisl','Ori Shepherd','Nullam feugiat placerat velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl. Maecenas malesuada fringilla est. Mauris eu turpis. Nulla aliquet. Proin velit. Sed malesuada augue ut lacus. Nulla tincidunt, neque vitae semper egestas, urna justo faucibus lectus, a sollicitudin orci sem eget massa. Suspendisse eleifend. Cras sed leo. Cras vehicula aliquet libero. Integer in magna. Phasellus dolor elit, pellentesque a, facilisis non, bibendum sed, est. Nunc laoreet lectus quis massa. Mauris vestibulum,',241,'Adventure'),\n"
                + "(?,?,'user3','Suspendisse aliquet, sem','Maite Ferguson','euismod enim. Etiam gravida molestie arcu. Sed eu nibh vulputate mauris sagittis placerat. Cras dictum ultricies ligula. Nullam enim. Sed nulla ante, iaculis nec, eleifend non, dapibus rutrum, justo. Praesent luctus. Curabitur egestas nunc sed libero. Proin sed turpis nec mauris blandit mattis. Cras eget nisi dictum augue malesuada malesuada. Integer id magna et ipsum cursus vestibulum.',432,'Adventure'),\n"
                + "(?,?,'user6','Sed molestie. Sed id risus','Claire Hogan','molestie tellus. Aenean egestas hendrerit neque. In ornare sagittis felis. Donec tempor, est ac mattis semper, dui lectus rutrum urna, nec luctus felis purus ac tellus. Suspendisse sed dolor. Fusce mi lorem, vehicula et, rutrum eu, ultrices sit amet, risus. Donec nibh enim, gravida sit amet, dapibus id, blandit at, nisi. Cum sociis natoque penatibus',908,'Adventure'),\n"
                + "(?,?,'user2','at, nisi. Cum sociis','Holmes E. English','risus, at fringilla purus mauris a nunc. In at pede. Cras vulputate velit eu sem. Pellentesque ut ipsum ac mi eleifend egestas. Sed pharetra, felis eget varius ultrices, mauris ipsum porta elit, a feugiat tellus lorem eu metus. In lorem. Donec elementum, lorem ut aliquam iaculis, lacus pede sagittis augue, eu tempor erat neque non quam. Pellentesque habitant morbi tristique senectus et netus et malesuada',832,'Adventure'),\n"
                + "(?,?,'user10','parturient','Omar I. Crane','nisi. Mauris nulla. Integer urna. Vivamus molestie dapibus ligula. Aliquam erat volutpat. Nulla dignissim. Maecenas ornare egestas lillentesque a, facilisis',203,'Adventure')\n";
             

        String insert_reviewrating = "INSERT INTO review_rating(username,bookID,review_text,rating) "
                + "VALUES\n"
                + "('user1',1,'sit amet lorem semper auctor. Mauris vel turpis. Aliquam adipiscing lobortis risus. In mi pede, nonummy ut, molestie in, tempus eu, ligula. Aenean euismod mauris eu elit. Nulla facilisi. Sed neque. Sed eget lacus. Mauris non dui nec urna suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus ornare. Fusce mollis. Duis sit amet diam eu dolor egestas rhoncus. Proin nisl sem, consequat nec, mollis vitae, posuere at, velit. Cras lorem lorem, luctus ut, pellentesque',4),\n"
                + "('user2',2,'Nunc sed orci lobortis augue scelerisque mollis. Phasellus libero mauris, aliquam eu, accumsan sed, facilisis vitae, orci. Phasellus dapibus quam quis diam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce aliquet magna a neque. Nullam ut nisi a odio semper cursus. Integer mollis. Integer tincidunt aliquam arcu. Aliquam ultrices iaculis odio. Nam interdum enim non nisi.',1),\n"
                + "('user3',3,'Duis gravida. Praesent eu nulla at sem molestie sodales. Mauris blandit enim consequat purus. Maecenas libero est, congue a, aliquet vel, vulputate eu, odio. Phasellus at augue id ante dictum cursus. Nunc mauris elit, dictum eu, eleifend nec, malesuada ut, sem. Nulla interdum. Curabitur dictum. Phasellus in felis. Nulla tempor augue ac ipsum. Phasellus vitae mauris sit amet lorem semper auctor. Mauris vel turpis. Aliquam adipiscing lobortis risus. In',2),\n"
                + "('user4',4,'ut nisi a odio semper cursus. Integer mollis. Integer tincidunt aliquam arcu. Aliquam ultrices iaculis odio. Nam interdum enim non nisi. Aenean eget metus. In nec orci. Donec nibh. Quisque nonummy ipsum non arcu. Vivamus sit amet risus. Donec egestas. Aliquam nec enim. Nunc ut erat. Sed nunc est, mollis non, cursus non, egestas a, dui. Cras pellentesque. Sed dictum. Proin eget odio. Aliquam vulputate ullamcorper magna. Sed eu eros. Nam consequat dolor vitae dolor. Donec fringilla. Donec feugiat metus',10),\n"
                + "('user5',5,'Etiam bibendum fermentum metus. Aenean sed pede nec ante blandit viverra. Donec tempus, lorem fringilla ornare placerat, orci lacus vestibulum lorem, sit amet ultricies sem magna nec quam. Curabitur vel lectus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec dignissim magna a tortor. Nunc commodo auctor velit. Aliquam nisl. Nulla eu neque pellentesque massa lobortis ultrices. Vivamus rhoncus. Donec est. Nunc ullamcorper,',10),\n"
                + "('user6',6,'ac ipsum. Phasellus vitae mauris sit amet lorem semper auctor. Mauris vel turpis. Aliquam adipiscing lobortis risus. In mi pede, nonummy ut, molestie in, tempus eu, ligula. Aenean euismod mauris eu elit. Nulla facilisi. Sed neque. Sed eget lacus. Mauris non dui nec urna suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus ornare. Fusce mollis. Duis sit amet diam eu dolor egestas rhoncus. Proin nisl sem, consequat nec, mollis vitae, posuere at,',6),\n"
                + "('user7',7,'at lacus. Quisque purus sapien, gravida non, sollicitudin a, malesuada id, erat. Etiam vestibulum massa rutrum magna. Cras convallis convallis dolor. Quisque tincidunt pede ac urna. Ut tincidunt vehicula risus. Nulla eget metus eu erat semper rutrum. Fusce dolor quam, elementum at, egestas a, scelerisque sed, sapien. Nunc pulvinar arcu et pede. Nunc sed orci lobortis augue scelerisque mollis.',1),\n"
                + "('user8',8,'quis, tristique ac, eleifend vitae, erat. Vivamus nisi. Mauris nulla. Integer urna. Vivamus molestie dapibus ligula. Aliquam erat volutpat. Nulla dignissim. Maecenas ornare egestas ligula. Nullam feugiat placerat velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl. Maecenas malesuada fringilla est. Mauris eu turpis. Nulla aliquet. Proin velit. Sed malesuada augue ut lacus. Nulla tincidunt, neque',7),('user9',9,'ipsum nunc id enim. Curabitur massa. Vestibulum accumsan neque et nunc. Quisque ornare tortor at risus. Nunc ac sem ut dolor dapibus gravida. Aliquam tincidunt, nunc ac mattis ornare, lectus ante dictum mi, ac mattis velit justo nec ante. Maecenas mi felis, adipiscing fringilla, porttitor vulputate, posuere vulputate, lacus. Cras interdum. Nunc sollicitudin commodo ipsum. Suspendisse non leo. Vivamus nibh dolor, nonummy ac, feugiat non, lobortis quis, pede. Suspendisse dui. Fusce diam nunc, ullamcorper eu, euismod ac, fermentum vel, mauris. Integer sem elit, pharetra',3),\n"
                + "('user10',10,'eu enim. Etiam imperdiet dictum magna. Ut tincidunt orci quis lectus. Nullam suscipit, est ac facilisis facilisis, magna tellus faucibus leo, in lobortis tellus justo sit amet nulla. Donec non justo. Proin non massa non ante bibendum ullamcorper. Duis cursus, diam at pretium aliquet, metus urna convallis erat, eget tincidunt dui augue eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus id, libero. Donec consectetuer mauris id sapien. Cras dolor',0),\n"
                + "('user11',11,'Fusce aliquet magna a neque. Nullam ut nisi a odio semper cursus. Integer mollis. Integer tincidunt aliquam arcu. Aliquam ultrices iaculis odio. Nam interdum enim non nisi. Aenean eget metus. In nec orci. Donec nibh. Quisque nonummy ipsum non arcu. Vivamus sit amet risus. Donec egestas. Aliquam nec enim. Nunc ut erat. Sed nunc est, mollis non, cursus non, egestas a, dui. Cras pellentesque. Sed dictum. Proin eget odio. Aliquam vulputate ullamcorper magna. Sed eu eros. Nam consequat dolor vitae dolor. Donec fringilla. Donec feugiat metus sit amet ante. Vivamus',9),\n"
                + "('user12',12,'Quisque fringilla euismod enim. Etiam gravida molestie arcu. Sed eu nibh vulputate mauris sagittis placerat. Cras dictum ultricies ligula. Nullam enim. Sed nulla ante, iaculis nec, eleifend non, dapibus rutrum, justo. Praesent luctus. Curabitur egestas nunc sed libero. Proin sed turpis nec mauris blandit mattis. Cras eget nisi dictum augue malesuada malesuada. Integer id magna et ipsum cursus vestibulum. Mauris magna. Duis dignissim tempor arcu. Vestibulum ut eros non enim commodo hendrerit. Donec porttitor tellus non magna. Nam ligula elit,',4),\n"
                + "('user13',13,'non enim commodo hendrerit. Donec porttitor tellus non magna. Nam ligula elit, pretium et, rutrum non, hendrerit id, ante. Nunc mauris sapien, cursus in, hendrerit consectetuer, cursus et, magna. Praesent interdum ligula eu enim. Etiam imperdiet dictum magna. Ut tincidunt orci quis lectus. Nullam suscipit, est ac facilisis facilisis, magna tellus faucibus leo, in lobortis tellus justo sit amet nulla. Donec non justo. Proin non massa non ante bibendum ullamcorper. Duis cursus, diam at pretium aliquet, metus urna convallis erat, eget tincidunt dui augue eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus id, libero. Donec',8),\n"
                + "('user14',14,'sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean eget magna. Suspendisse tristique neque venenatis lacus. Etiam bibendum fermentum metus. Aenean sed pede nec ante blandit viverra. Donec tempus, lorem fringilla ornare placerat, orci lacus vestibulum lorem, sit amet ultricies sem magna nec quam. Curabitur vel lectus. Cum sociis',8),\n"
                + "('user15',15,'ut dolor dapibus gravida. Aliquam tis gravida. Aliquam tincidus gravida. Aliquam tincidus gravida. Aliquam tincidus gravida. Aliquam tincidus gravida. Aliquam tincidus gravida. Aliquam tincidus gravida. Aliquam tinciduncidunt, nunque',9)";

        String insert_readinghistory = "INSERT INTO readinghistory(username,bookID,reading_duration) "
                + "VALUES"
                + "('user1',2,608),('user1',5,60),('user10',8,190),('user13',8,308),('user16',1,387),"
                + "('user19',3,358),('user2',3,57),('user5',8,615),('user8',3,969),('user3',9,380),"
                + "('user3',2,855),('user7',8,642),('user4',16,259),('user4',4,131),"
                + "('user1',3,608),('user1',15,60),('user10',18,190),('user13',18,308),('user16',11,387),"
                + "('user19',5,358),('user2',13,57),('user5',18,615),('user8',13,969),('user3',19,380),"
                + "('user3',12,855),('user7',18,642),('user4',16,259),('user4',14,131)";
/*
        String insert_message = "INSERT INTO message(sender,receiver,message_txt)"
                + " VALUES"
                + "('user1','user2','auctor non, feugiat nec, diam. Duis mi enim, condimentum eget, volutpat ornare, facilisis eget, ipsum. Donec sollicitudin adipiscing ligula. Aenean gravida nunc sed pede. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus'),\n"
                + "('user3','user4','inceptos hymenaeos. Mauris ut quam vel sapien imperdiet ornare. In faucibus. Morbi vehicula. Pellentesque tincidunt tempus'),(\n"
                + "('user5','user6','convallis convallis dolor. Quisque tincidunt pede ac urna. Ut tincidunt vehicula risus. Nulla eget metus eu erat semper rutrum. Fusce dolor quam, elementum at, egestas a, scelerisque sed, sapien. Nunc pulvinar arcu et pede. Nunc sed orci lobortis augue scelerisque mollis. Phasellus libero mauris, aliquam eu, accumsan sed, facilisis'),('user7','user8','enim non nisi. Aenean eget metus. In nec orci. Donec nibh. Quisque nonummy ipsum non arcu. Vivamus sit amet risus.'),('user9','user10','rutrum non, hendrerit id, ante. Nunc mauris sapien, cursus in, hendrerit consectetuer, cursus et, magna. Praesent interdum ligula eu enim. Etiam imperdiet dictum magna. Ut tincidunt orci quis lectus. Nullam suscipit, est ac'),('user11','user12','erat volutpat. Nulla dignissim. Maecenas ornare egestas ligula. Nullam feugiat placerat velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl. Maecenas malesuada fringilla est. Mauris eu'),('user13','user14','tempor augue ac ipsum. Phasellus vitae mauris sit amet lorem semper auctor. Mauris vel turpis. Aliquam adipiscing lobortis'),\n"
                + "('user15','user16','nonummy ac, feugiat non, lobortis quis, pede. Suspendisse dui. Fusce diam nunc, ullamcorper eu, euismod ac, fermentum vel, mauris. Integer sem elit, pharetra ut, pharetra sed, hendrerit a,'),\n"
                + "('user17','user18','in felis. Nulla tempor augue ac ipsum. Phasellus vitae mauris sit amet lorem semper auctor. Mauris vel turpis. Aliquam adipiscing lobortis risus. In mi pede, nonummy ut, molestie in, tempus'),\n"
                + "('user19','user20','rutrum, justo. Praesent luctus. Curabitur egestas nunc sed libero. Proin sed turpis nec mauris blandit mattis. Cras eget nisi dictum augue malesuada malesuada. Integer id magna et ipsum cursus vestibulum.'),\n"
                + "('user2','user20','tempus, lorem fringilla ornare placerat, orci lacus vestibulum lorem, sit amet ultricies sem magna nec quam.'),('user23','user24','pellentesque. Sed dictum. Proin eget odio. Aliquam vulputate ullamcorper magna. Sed eu eros. Nam consequat dolor vitae dolor. Donec fringilla. Donec feugiat metus sit amet ante. Vivamus non lorem vitae odio sagittis'),\n"
                + "('user2','user20','nulla. Donec non justo. Proin non massa non ante bibendum ullamcorper. Duis cursus, diam at pretium aliquet, metus urna convallis erat, eget'),('user27','user28','mattis. Cras eget nisi dictum augue malesuada malesuada. Integer id magna et ipsum cursus vestibulum. Mauris magna. Duis dignissim tempor arcu. Vestibulum ut eros non enim commodo hendrerit. Donec porttitor tellus non'),\n"
                + "('user9','user3','ac mattis semper, dui lectus rutrum urna, nec luctus felis purus ac tellus. Suspendisse sed dolor. Fusce mi lorem, vehicula et, rutrum eu, ultrices sit amet, risus. Donec'),('user31','user32','lectus pede et risus. Quisque libero lacus, varius et, euismod et, commodo at, libero. Morbi accumsan laoreet ipsum. Curabitur consequat, lectus sit amet luctus vulputate, nisi sem semper erat, in consectetuer ipsum nunc id enim. Curabitur massa. Vestibulum accumsan neque et nunc. Quisque ornare'),\n"
                + "('user3','user3','Cras dolor dolor, tempus non, lacinia at, iaculis quis, pede. Praesent eu dui. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus'),('user35','user36','laoreet lectus quis massa. Mauris vestibulum, neque sed dictum eleifend, nunc risus varius orci, in consequat enim diam vel arcu. Curabitur ut odio vel est tempor bibendum. Donec felis orci, adipiscing'),\n"
                + "('user3','user3','nisi a odio semper cursus. Integer mollis. Integer tincidunt aliquam arcu. Aliquam ultrices iaculis odio. Nam interdum enim non nisi. Aenean eget metus. In nec orci. Donec'),\n"
                + "('user3','user4','Donec vitae erat vel pede blandit congue. In scelerisque scelerisque dui. Suspendisse ac'),('user41','user42','nibh lacinia orci, consectetuer euismod est arcu ac orci. Ut semper pretium neque. Morbi quis urna. Nunc quis arcu vel quam dignissim pharetra.'),\n"
                + "('user4','user4','ullamcorper, velit in aliquet lobortis, nisi nibh lacinia orci, consectetuer euismod est arcu ac orci. Ut semper pretium neque. Morbi quis urna. Nunc quis'),(\n"
                + "'user4','user8','ligula. Donec luctus aliquet odio. Etiam ligula tortor, dictum eu, placerat eget, venenatis a, magna. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam laoreet, libero et tristique pellentesque, tellus sem mollis dui, in'),\n"
                + "('user4','user8','Sed nulla ante, iaculis nec, eleifend non, dapibus rutrum, justo. Praesent luctus. Curabitur egestas nunc sed libero. Proin sed turpis nec mauris blandit mattis. Cras eget nisi dictum augue malesuada malesuada. Integer id magna et ipsum cursus vestibulum. Mauris magna. Duis'),\n"
                + "('user4','user5','Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec tincidunt. Donec vitae erat vel pede blandit congue. In scelerisque scelerisque dui. Suspendisse ac metus vitae velit egestas lacinia. Sed congue, elit sed consequat auctor, nunc nulla vulputate dui,'),\n"
                + "('user1','user3','vel quam dignissim pharetra. Nam ac nulla. In tincidunt congue turpis. In condimentum. Donec at arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec tincidunt. Donec vitae erat vel pede blandit congue. In scelerisque scelerisque dui. Suspendisse ac metus vitae velit egestas lacinia.'),\n"
                + "('user4','user7','adipiscing. Mauris molestie pharetra nibh. Aliquam ornare, libero at auctor ullamcorper, nisl arcu iaculis enim, sit amet ornare lectus justo eu arcu. Morbi sit amet'),\n"
                + "('user7','user11','Vivamus nisi. Mauris nulla. Integer urna. Vivamus molestie dapibus ligula. Aliquam erat volutpat. Nulla dignissim. Maecenas ornare egestas ligula. Nullam feugiat placerat velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl. Maecenas malesuada fringilla est. Mauris eu turpis. Nulla aliquet. Proin velit. Sed malesuada augue ut lacus. Nulla tincidunt, neque'),\n"
                + "('user10','user15','non enim. Mauris quis turpis vitae purus gravida sagittis. Duis gravida. Praesent eu nulla at sem molestie sodales. Mauris blandit enim consequat purus. Maecenas libero est, congue a, aliquet vel, vulputate eu, odio. Phasellus at augue id ante dictum cursus. Nunc mauris elit, dictum eu, eleifend nec, malesuada ut,'),\n"
                + "('user13','user9','felis, adipiscing fringilla, porttitor vulputate, posuere vulputate, lacus. Cras interdum. Nunc sollicitudin commodo ipsum. Suspendisse non leo. Vivamus nibh dolor, nonummy ac, feugiat non, lobortis quis, pede. Suspendisse dui. Fusce diam nunc, ullamcorper eu, euismod'),\n"
                + "('user16','user2','Integer aliquam adipiscing lacus. Ut nec urna et arcu imperdiet ullamcorper. Duis at lacus. Quisque purus sapien, gravida non, sollicitudin a, malesuada id, erat. Etiam vestibulum massa rutrum'),\n"
                + "('user19','user2','eget, ipsum. Donec sollicitudin adipiscing ligula. Aenean gravida nunc sed pede. Cum sociis natoque penatibus et magnis'),('user22','user31','gravida sagittis. Duis gravida. Praesent eu nulla at sem molestie sodales. Mauris blandit enim consequat purus. Maecenas libero est, congue a, aliquet vel, vulputate eu, odio. Phasellus at augue id ante dictum cursus. Nunc mauris elit, dictum eu,'),"
                + "('user5','user3','nascetur ridiculus mus. Donec dignissim magna a tortor. Nunc commodo auctor velit. Aliquam nisl. Nulla eu neque pellentesque massa lobortis ultrices. Vivamus rhoncus.'),\n"
                + "('user8','user3','mi lacinia mattis. Integer eu lacus. Quisque imperdiet, erat nonummy ultricies ornare, elit elit fermentum risus, at fringilla purus mauris a nunc. In at pede. Cras vulputate velit'),\n"
                + "('user13','user4','ipsum. Suspendisse sagittis. Nullam vitae diam. Proin dolor. Nulla semper tellus id nunc interdum'),\n"
                + "('user4','user4','sed pede. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin vel arcu eu odio tristique pharetra. Quisque ac libero nec ligula consectetuer rhoncus. Nullam velit dui, semper et, lacinia vitae, sodales at, velit. Pellentesque ultricies dignissim lacus. Aliquam rutrum'),\n"
                + "('user7','user12','sit amet ultricies sem magna nec quam. Curabitur vel lectus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec dignissim magna a tortor. Nunc commodo auctor velit. Aliquam'),\n"
                + "('user10','user2','in sodales elit erat vitae risus. Duis a mi fringilla mi lacinia mattis. Integer eu lacus. Quisque imperdiet, erat nonummy ultricies ornare, elit elit fermentum risus, at fringilla purus mauris'),\n"
                + "('user4','user1','arcu. Curabitur ut odio vel est tempor bibendum. Donec felis orci, adipiscing non, luctus sit amet, faucibus ut, nulla. Cras')";
*/
        String insert_invitation = "INSERT INTO invitation(inviter,invitee,sharing_points,bookID) "
                + "VALUES('user2','user3',5,3),('user4','user7',408,2),('user6','user11',929,3),('user8','user15',737,4),('user10','user19',994,2),"
                + "('user12','user2',602,3),('user14','user7',140,5),('user16','user3',406,2),('user18','user3',106,2),"
                + "('user20','user3',2,2),('user2','user4',171,14),('user4','user7',358,9),('user6','user17',457,4),"
                + "('user8','user5',892,18),('user3','user19',961,4),('user2','user3',439,3),('user4','user7',307,7),\n"
                + "('user6','user17',43,2),('user8','user5',76,12),('user4','user9',428,2),('user4','user16',240,3),('user4','user17',319,2),"
                + "('user4','user11',474,16),('user4','user5',643,2),('user5','user9',353,2),('user2','user13',232,18),('user14','user17',228,10),"
                + "('user6','user11',140,7),('user8','user15',184,10),('user19','user3',744,5),('user1','user9',397,3)";
              
        String insert_complaint = "INSERT INTO complaint(username,bookID,complaint_text) "
                + "VALUES"
                + "('user3',5,'arcu. Morbi sit amet massa. Quisque porttitor eros nec tellus. Nunc lectus pede, ultrices a, auctor non, feugiat nec, diam. Duis mi enim, condimentum eget, volutpat ornare,'),\n"
                + "('user6',1,'litora torquent per conubia nostra, per inceptos hymenaeos. Mauris ut quam vel sapien imperdiet ornare. In faucibus. Morbi vehicula. Pellentesque tincidunt tempus risus. Donec egestas. Duis ac arcu. Nunc mauris. Morbi non sapien molestie orci tincidunt adipiscing. Mauris molestie pharetra nibh. Aliquam ornare, libero at auctor ullamcorper,'),\n"
                + "('user9',11,'dignissim magna a tortor. Nunc commodo auctor velit. Aliquam nisl. Nulla eu neque pellentesque massa lobortis ultrices. Vivamus rhoncus. Donec est. Nunc ullamcorper, velit in aliquet lobortis, nisi nibh lacinia orci, consectetuer euismod est arcu ac orci. Ut semper pretium neque. Morbi'),\n"
                + "('user12',8,'sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean eget magna. Suspendisse tristique neque venenatis lacus. Etiam bibendum fermentum metus. Aenean sed pede nec ante blandit viverra. Donec tempus, lorem fringilla ornare placerat, orci lacus vestibulum lorem, sit amet ultricies sem magna nec'),\n"
                + "('user15',1,'hendrerit consectetuer, cursus et, magna. Praesent interdum ligula eu enim. Etiam imperdiet dictum magna. Ut tincidunt orci quis lectus. Nullam suscipit, est ac facilisis facilisis, magna tellus faucibus leo, in lobortis tellus justo sit amet nulla. Donec non justo. Proin non massa non ante bibendum ullamcorper. Duis cursus, diam at pretium aliquet, metus urna convallis erat, eget tsellus'),\n"
                + "('user18',9,'Cras eu tellus eu augue porttitor interdum. Sed auctor odio a purus. Duis elementum, dui quis accumsan convallis, ante lectus convallis est, vitae sodales nisi magna sed dui. Fusce aliquam, enim nec tempus scelerisque, lorem ipsum sodales purus, in molestie tortor nibh sit amet orci.it. Pellentesque ultricies dignissim lacus. Aliquam rutrum lorem ac')";
              
                
        PopulateDummyData p = new PopulateDummyData();
        URL path = PopulateDummyData.class.getResource(p.getClassName() + ".class");
        String filepath = path.toString();
        String currentDir = filepath.substring(filepath.indexOf("/"), filepath.lastIndexOf("/"));

        System.out.println("populate data from current working directory:\n" + currentDir);

        DbConnector dbc = new DbConnector();
        Connection conn = dbc.Connects();
        PreparedStatement pst = null;
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        //insert userinfo
        pst = conn.prepareStatement(insert_userinfo);
        pst.execute();
        System.out.println("Populate Date to userinfo.");

   
      //insert bookinfo
        //set 20 cover pages for bookinfo
        pst = conn.prepareStatement(insert_bookinfo);

        for (int num = 1; num <= 20; num++) {

            String coverPath = currentDir + "/PopulateDummyData/cover_" + num + ".jpg";   
            InputStream inputBookCover = new FileInputStream(new File(coverPath));
            String bookfilePath = currentDir + "/PopulateDummyData/bookfile_" + num + ".txt";
            InputStream inputBookFile = new FileInputStream(new File(bookfilePath));

            int coverPosition_insert = num * 2 - 1;
            pst.setBlob(coverPosition_insert, inputBookCover);
            pst.setBlob(coverPosition_insert + 1, inputBookFile);

        }
        pst.execute();
        System.out.println("Populate Data to TABLE bookinfo.");

//insert pendingbook
        pst = conn.prepareStatement(insert_pendingbook);
 
         //set value position, after bookinfo. 
       
        for (int num = 1; num <= 10; num++) {

            String coverPath = currentDir + "/PopulateDummyData/cover_" + num + ".jpg";
            InputStream inputBookCover = new FileInputStream(new File(coverPath));
            String bookfilePath = currentDir + "/PopulateDummyData/bookfile_" + num + ".txt";
            InputStream inputBookFile = new FileInputStream(new File(bookfilePath));

            int coverPosition_insert = num * 2 - 1;
            pst.setBlob(coverPosition_insert, inputBookCover);
            pst.setBlob(coverPosition_insert + 1, inputBookFile);
     

        }

        pst.execute();
        System.out.println("Populate Data to TABLE pendingbook.");

        conn.setAutoCommit(false);
      //stmt.addBatch()

       //insert other tables  
        stmt.addBatch(insert_reviewrating);
        stmt.addBatch(insert_readinghistory);
       stmt.addBatch(insert_complaint);
       stmt.addBatch(insert_invitation);
        //stmt.addBatch(insert_message);

        stmt.executeBatch();
        conn.commit();
               

        System.out.println("Populate Data to TABLE reviewrating");
        System.out.println("Populate Data to TABLE readinghistory");
        System.out.println("Populate Data to TABLE complaint");
        System.out.println("Populate Data to TABLE inviatation");
        System.out.println("Populate Data to TABLE message");

        System.out.println("Run Sucessfully.\nBatch executed.\nData For All Tables Populated.");

    }

}
