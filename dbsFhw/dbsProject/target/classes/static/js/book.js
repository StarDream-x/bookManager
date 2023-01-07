var vue = new Vue({
    el: '#app',
    data: {
        keyword: "",
        books: [], //查询结果
        currentBook: {}, //当前编辑的事项
        dialogVisible: false, //对话框是否显示
        editMode: false,  //当前是否是编辑模式（还是添加模式）
        currentPage:1,
        totalCount:1,
        pageSizes:[10,20,30,40],
        PageSize:10,
        imgUrl: "https://img.alicdn.com/tfs/TB161Wer1uSBuNjy1XcXXcYjFXa-2528-1266.png",
        detailVisible: false
    },
    methods: {
        query: function (keyword) {
            var path = '/books';
            if (this.keyword != "") path = path + "?name=" + this.keyword;
            var self = this
            axios.get(path)
                .then(response =>{
                    self.books = response.data;
                    this.totalCount =  this.books.length;
                })
                .catch(e => self.$message.error(e.response.data))
        },
        deleteBook: function (book) {
            var self = this
            axios.delete('/books/' + book.id)
                .then(response => self.query())
                .catch(e => self.$message.error(e.response.data))
        },
        showEdit: function (book) {
            this.dialogVisible = true
            this.editMode = true;
            this.currentBook = Object.assign({}, book)
        },
        showAdd: function (book) {
            this.detailVisible=false
            this.dialogVisible = true
            this.editMode = false;
            this.currentBook = {complete: false}
        },
        saveBook: function () {
            var self = this
            if (self.editMode) {
                axios.put('/books/' + self.currentBook.id, self.currentBook)
                    .then(response => self.query())
                    .catch(e => self.$message.error(e.response.data))
            } else {
                axios.post('/books', self.currentBook)
                    .then(response => self.query())
                    .catch(e => self.$message.error(e.response.data))
            }
            this.dialogVisible = false
        },
        handleSizeChange(val) {
            // 改变每页显示的条数
            this.PageSize=val
            // 注意：在改变每页显示的条数时，要将页码显示到第一页
            this.currentPage=1
        },
        // 显示第几页
        handleCurrentChange(val) {
            // 改变默认的页数
            this.currentPage=val
        },
        showImg(row){
            this.currentBook=row
            this.imgUrl=this.currentBook.imgUrl
            if(this.imgUrl == null) this.imgUrl = "https://img.alicdn.com/tfs/TB161Wer1uSBuNjy1XcXXcYjFXa-2528-1266.png"
            if(!this.dialogVisible) this.detailVisible=true
        }
    }
})
vue.query();