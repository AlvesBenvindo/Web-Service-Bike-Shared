package ao.uan.fc.cc4.bikeshared.Station1.bd.dock;

import javax.persistence.*;

@Entity
@Table(name = "docks")
public class DockModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "reference", unique = true, nullable = false)
    private String reference;
    @Column(name = "state")
    protected Integer state;
    @Column(name = "info")
    protected Integer info;

    public DockModel() {}

    public DockModel(Long id, String reference, String info, Integer state) {
        this.id = id;
        this.reference = reference;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    
}
