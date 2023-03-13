package android.bignerdranch.drifting.Mine;

public class Items {
    private String name;
    private String theme;
    private String cover;
    private String item_kind;//类型:漂流本，漂流相机，漂流小说，漂流画
    private Long kind;//生人熟人模式
    private Long nowamount;//目前参与人数/收集数量
    private Long maxamount;//最大参与人数/收集数量
    private String create_time;//创建时间
    private Long id;//id

    public Items(String name,String theme,String cover,String item_kind,Long kind,Long nowamount,Long maxamount,String create_time,Long id){
        this.name = name;
        this.theme = theme;
        this.cover = cover;
        this.item_kind = item_kind;
        this.kind = kind;
        this.nowamount = nowamount;
        this.maxamount = maxamount;
        this.create_time = create_time;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getItem_kind() {
        return item_kind;
    }

    public void setItem_kind(String item_kind) {
        this.item_kind = item_kind;
    }

    public Long getKind() {
        return kind;
    }

    public void setKind(Long kind) {
        this.kind = kind;
    }

    public Long getNowamount() {
        return nowamount;
    }

    public void setNowamount(Long nowamount) {
        this.nowamount = nowamount;
    }

    public Long getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(Long maxamount) {
        this.maxamount = maxamount;
    }
}
