var vue = new Vue({
    el: '#app',
    data: {
        // keyword: "",
        pTitle:"",
        pDate: "",
        pAuthor: "",
        pUpDate: "",
        pUpId: null,
        papers: [], //查询结果
        currentPaper: {}, //当前编辑的事项
        dialogVisible: false, //对话框是否显示
        editMode: false, //当前是否是编辑模式（还是添加模式）
        currentPage:1,
        totalCount:1,
        pageSizes:[10,20,30,40],
        PageSize:10,
        // imgUrl: "https://img.alicdn.com/tfs/TB161Wer1uSBuNjy1XcXXcYjFXa-2528-1266.png",
        detailVisible: false
    },
    methods: {
        query: function (keyword) {
            var path = '/papers?pNum=' + this.currentPage + "&&pSize=" + this.PageSize
            if (this.pTitle && this.pTitle != "") path = path + "&&name=" + this.pTitle
            if(this.pAuthor && this.pAuthor != "") path = path + "&&author=" + this.pAuthor
            if(this.pUpDate && this.pUpDate != "") path = path + "&&suploadDate=" + this.pUpDate[0] + "&&euploadDate=" + this.pUpDate[1]
            if(this.pDate && this.pDate != "") path = path + "&&spaperDate=" + this.pDate[0] + "&&epaperDate=" + this.pDate[1]
            if(this.pUpId && this.pUpId != null) path = path + "&&uploader=" +this.pUpId

            var self = this
            axios.get(path)
                .then(response =>{
                    self.papers = response.data.content;
                    this.totalCount =  response.data.totalElements;
                })
                .catch(e => self.$message.error(e.response.data))
            this.currentPage = 1

        },
        deletePaper: function (paper) {
            var self = this
            axios.delete('/papers/' + paper.paperId)
                .then(response => self.query())
                .catch(e => self.$message.error(e.response.data))
        },
        showEdit: function (paper) {
            this.dialogVisible = true
            this.editMode = true;
            this.currentPaper = Object.assign({}, paper)
            // console.log(this.currentPaper)
        },
        showAdd: function (paper) {
            this.dialogVisible = true
            this.editMode = false;
            // this.currentPaper = {complete: false}
            this.currentPaper = {complete: false}
        },
        savePaper: function () {
            var self = this
            if (self.editMode) {
                axios.put('/papers/' + self.currentPaper.paperId, self.currentPaper)
                    .then(response => self.query())
                    .catch(function (error) {
                        self.$message.error("违反外键约束，请检查上传者是否存在")
                    });
            } else {
                axios.post('/papers', self.currentPaper)
                    .then(response => self.query())
                    .catch(e => self.$message.error("违反外键约束，请检查上传者是否存在"))
            }
            this.dialogVisible = false
        },
        handleSizeChange(val) {
            // 改变每页显示的条数
            this.PageSize=val
            // 注意：在改变每页显示的条数时，要将页码显示到第一页
            this.currentPage=1
            this.query()
        },
        // 显示第几页
        handleCurrentChange(val) {
            // 改变默认的页数
            this.currentPage=val
            this.query()
        },
        clearSiev(){
            this.pTitle = ""
            this.pDate = ""
            this.pAuthor = ""
            this.pUpId = null
            this.pUpDate = ""
            this.query()
        }
    }
})
vue.query();
