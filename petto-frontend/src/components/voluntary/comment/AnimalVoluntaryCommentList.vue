<template>
    <div style="margin-left:5%;">
        <div v-if="!volComments || (Array.isArray(volComments) && volComments.length === 0)">
            <span colspan="4">
                댓글이 없습니다.
            </span>
        </div>
        <div v-for="volComment in volComments" :key="volComment.commentNo" style="margin:20px;">
                <span align="left" style="width:5%;"><v-icon>person</v-icon></span>
                <span align="left"  style="width:15%;"><b>{{volComment.writer}}</b></span>

                <span align="left" style="width:50%; vertical-align: middle;">
                    {{volComment.comment}}
                </span>
                <span align="left" style="width:15%; vertical-align: bottom; margin-left:10px;" >
                    {{volComment.updDate}}
                </span>

                <span v-if="user == volComment.writer" align="left" style="vertical-align: bottom;">
                    <v-btn icon @click="editTextOpen(volComment.commentNo)">
                    <v-icon>edit</v-icon></v-btn>
                </span>
                    
                <span v-if="user == volComment.writer" align="left" style="vertical-align: bottom;">
                    <v-btn @click="onDelete(volComment.commentNo)" icon v-if="isEdit">
                        <v-icon>close</v-icon></v-btn>
                </span>
        
                <span v-if="!isEdit && user == volComment.writer && vols == volComment.commentNo">
                    <v-textarea outlined type="comment" name="comment" v-model="comment" hint="수정할 댓글을 입력하세요."
                        style="width:50em; margin:1em; display:inline-block;"></v-textarea>
                    <v-btn @click="onEdit(volComment.commentNo)" style="margin-top:3em;" color="orange" icon v-if="!isEdit && user == volComment.writer"><v-icon>done</v-icon></v-btn>
                </span>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AnimalVoluntaryCommentList',
    props: {
        volComments: {
            type: Array
        }
    },
    data() {
        return {
            user:'',
            isEdit: true,
            comment:'',
            vols:''
        }
    },
    mounted() {
        if(this.$cookies.isKey("user") == true){
            this.user = this.$cookies.get("user").nickname
        } else {
            this.user = '비회원'
        }
    },
    methods: {
        editTextOpen(commentsNo) {
            this.commentNo = commentsNo
            for(var i=0; i <this.volComments.length; i++ ) {
                if(this.volComments[i].commentNo == this.commentNo){
                    this.vols = this.commentNo
                }
            }
            this.isEdit = !this.isEdit
        },
        onDelete(commentsNo) {
            this.commentNo = commentsNo
            axios.delete(`http://localhost:8888/petto/comments/${this.commentNo}`)
                .then(() => {
                    alert('댓글을 삭제했습니다.')
                    location.reload(true);
                })
               .catch(res => {
                    alert(res.response.data.message)
                })
        },
        onEdit(commentsNo) {
            this.commentNo = commentsNo
            const { comment } = this
            axios.put(`http://localhost:8888/petto/comments/edit/${this.commentNo}`, { comment })
                    .then(() => {
                        alert('수정하였습니다.')
                        location.reload(true);
                    })
                     .catch(err => {
                        alert(err.response.data.message)
                    })
        }

    }
}
</script>

<style scoped>

span {
    display: inline-block;
}

</style>
