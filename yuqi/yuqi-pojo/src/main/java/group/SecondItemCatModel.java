package group;

import com.yuqi.pojo.TbItemCat;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date: Create in 21:06 2019/5/3
 */
public class SecondItemCatModel implements Serializable {
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
   private List<TbItemCat> tbItemCats;

    public List<TbItemCat> getTbItemCats() {
        return tbItemCats;
    }

    public void setTbItemCats(List<TbItemCat> tbItemCats) {
        this.tbItemCats = tbItemCats;
    }
}
