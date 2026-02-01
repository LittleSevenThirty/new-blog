import { defineStore } from "pinia";
import { shallowRef } from "vue";

const useLoadingStore = defineStore("loading",()=>{
    const isLoading=shallowRef<boolean>(false);

    function show(){
        // 效果展示，禁止显现滚动条
        document.body.style.overflow='hidden';
        isLoading.value=true;
    }

    function hidden(){
        // 效果隐藏
        document.body.style.overflow='';
        isLoading.value=false;
    }

    return {
        isLoading,
        show,
        hidden
    }
});

export default useLoadingStore;