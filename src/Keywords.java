//import aims.app.generic.logger.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQuery(name = "Keywords.findAll", query = "select o from Keywords o")
public class Keywords implements Serializable {
    @Id
    @Column(nullable = false)
    private String keyword;

    public Keywords() {

    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean equals(Object o) {

//        LoggerFactory.LogInfo("start");
        if (o == null) {
//            LoggerFactory.LogInfo("o is null");
            return false;
        }
        if (! (o instanceof  Keywords)) {
//            LoggerFactory.LogInfo("not a keywords");
            return false;
        }

        Keywords other = (Keywords) o;
        
        if (this.getKeyword() == null)  {
//            LoggerFactory.LogInfo("this keyword null");
            return other.getKeyword()==null;
        }

//        LoggerFactory.LogInfo(this.getKeyword() + " " + other.getKeyword());
        return this.getKeyword().equals(other.getKeyword());
    }


}
