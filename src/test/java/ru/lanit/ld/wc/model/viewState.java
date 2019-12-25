package ru.lanit.ld.wc.model;

import com.google.gson.JsonObject;

public class viewState {
    private int collectionViewMode;
    private boolean isPreview;
    private int page;
    private int pageSize;
    private boolean preview;
    private double previewSize;
    private int selectedId;

    private String cellName;
    private String text;
    private boolean isAsc;
    private boolean isDate; //это т параметр появляется только если во фронте выбрана дата

    private boolean unreadOnlyFilter;

    public viewState() {
        collectionViewMode = 1;
        isPreview = false;
        page = 1;
        pageSize = 10;
        preview = false;
        previewSize = 43.61;
        selectedId = 0;
        //sortField: {cellName: "createDateTime", text: "Дата создания", isAsc: false, isDate: true}
        cellName = "createDateTime";
        isAsc = false;
        isDate = true;
        text = "Дата создания";
        unreadOnlyFilter = false;
    }

    public JsonObject toJson() {
        JsonObject state = new JsonObject();
        JsonObject sortField = new JsonObject();

        state.addProperty("collectionViewMode", this.collectionViewMode);
        state.addProperty("isPreview", this.isPreview);
        state.addProperty("page", this.page);
        state.addProperty("pageSize", this.pageSize);
        state.addProperty("previewSize", this.previewSize);

        if (this.selectedId==0){
            state.add("selectedId",null);
        }else { state.addProperty("selectedId", String.format("%s", this.selectedId));}

        sortField.addProperty("cellName", this.cellName);
        sortField.addProperty("text", this.text);
        sortField.addProperty("isAsc", this.isAsc);
        if (this.cellName.equals("createDateTime")) {
            sortField.addProperty("isDate", this.isDate);
        }

        state.add("sortField", sortField);
        state.addProperty("unreadOnlyFilter", this.unreadOnlyFilter);

        return state;

    }


}
