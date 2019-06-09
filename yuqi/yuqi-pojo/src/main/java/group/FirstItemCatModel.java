package group;


import java.io.Serializable;
import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date: Create in 21:07 2019/5/3
 */
public class FirstItemCatModel implements Serializable {
    private Long id;

    private Long parentId;

    private String name;

    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    private List<SecondItemCatModel> secondItemCatModels;

    public List<SecondItemCatModel> getSecondItemCatModels() {
        return secondItemCatModels;
    }

    public void setSecondItemCatModels(List<SecondItemCatModel> secondItemCatModels) {
        this.secondItemCatModels = secondItemCatModels;
    }
}
