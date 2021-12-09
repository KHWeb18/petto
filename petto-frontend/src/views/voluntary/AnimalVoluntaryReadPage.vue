<template>
    <div id="voluntaryboard">


        <animal-voluntary-read v-if="voluntaryboard" :voluntaryboard="voluntaryboard"/>

        <p v-else>로딩중 ...... </p>

        <v-container class="justify center" style="margin-bottom:5%; margin-top:5%;" >
            <v-col cols="12" md="12"> 
            <v-btn color="orange" 
            class="ma-2 white--text" route :to="{ name: 'AnimalVoluntaryListPage' }">
            목록보기
            </v-btn> 
            <v-btn type="submit" color="orange darken-2" v-if="this.access == '관리자'"
            class="ma-2 white--text" route :to="{ name: 'AnimalVoluntaryModifyPage', params: { volunteerNo } }">
            게시글 수정
            </v-btn>
            <v-btn color="orange darken-2" outlined v-if="this.access == '관리자'"
            class="ma-2 white--text" @click="onDelete">
            게시글 삭제
            </v-btn>
            </v-col>   
        </v-container>

        <v-divider></v-divider>

        <v-container class="justify center">
            <v-textarea outlined type="comment" name="comment" v-model="comment"
             style="width:90%;float:left; margin-top:1em;"></v-textarea>
            <v-btn style="height:5em;" color="orange" class="ma-3 white--text" @click="onSubmit()">확인</v-btn>
        </v-container>
  
        <v-container style="margin-bottom:5%">
            <animal-voluntary-comment-list :volComments="volComments"/>
        </v-container>

        <v-divider></v-divider>
    </div>
</template>

<script>
import AnimalVoluntaryRead from '@/components/voluntary/AnimalVoluntaryRead.vue'
import AnimalVoluntaryCommentList from '@/components/voluntary/comment/AnimalVoluntaryCommentList.vue'
import { mapState, mapActions } from 'vuex'
import axios from 'axios'


export default {
    name: 'AnimalVoluntaryReadPage',
    props: {
        volunteerNo: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            comment: '',
            userInfo: this.$cookies.isKey("user"),
            writer: '',
            access:''
        }
    } ,
    mounted() {
        if(this.userInfo == true) {
            this.$store.state.session = this.$cookies.get("user")
            this.access = this.$cookies.get("user").auth
        } else {
            this.access = '비회원'
        }
        this.fetchVolCommentList(this.volunteerNo)
    },
    components: {
        AnimalVoluntaryRead,
        AnimalVoluntaryCommentList,
        
    },
    computed: {
        ...mapState(['voluntaryboard','volComments'])
    },
    created () {
        this.fetchVoluntaryBoard(this.volunteerNo)
                .catch(err => {
                    alert(err.response.data.message)
                    this.$router.push()
        })
    },
    methods: {
        ...mapActions(['fetchVoluntaryBoard','fetchVolCommentList']),
        onSubmit() {
                if(this.userInfo == true) {
                const volunteerNo = this.volunteerNo
                const writer = this.$cookies.get("user").nickname
                const { comment } = this
                const memberNo = this.$store.state.session.memberNo

                axios.post('http://localhost:8888/petto/comments/register', { volunteerNo,writer,comment, memberNo })
                .then(() => {
                    alert('댓글이 입력되었습니다.')
                    location.reload(true);
                })
                .catch(res => {
                    alert(res.response.data.message)
                })
                } else {
                    alert('로그인이 필요한 서비스입니다.')
                }
            
        },
        onDelete () {
            const { volunteerNo } = this.voluntaryboard
            axios.delete(`http://localhost:8888/petto/voluntaryBoard/${volunteerNo}`)
                .then(() => {
                    alert('게시글을 삭제했습니다.')
                    this.$router.push({ name: 'AnimalVoluntaryListPage' })
                })
               .catch(res => {
                    alert(res.response.data.message)
                })
        }
    }
}
</script>

