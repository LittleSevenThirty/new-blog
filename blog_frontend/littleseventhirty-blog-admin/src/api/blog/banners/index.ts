// banner轮播图相关接口
import { message } from "ant-design-vue";

// 上传轮播图 --
export async function uploadBanner(data: any, handleProgress: any) {
  return usePost('/banner/upload/banner', data, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    onUploadProgress: handleProgress,
  })
}

// 后台查询banner列表 --
export async function backGetBanners() {
  return useGet('/banner/back/slideshow')
}

// 删除banner --
export async function deleteBanner(bannerId: any) {
  return useDelete(`/banner/${bannerId}`)
}

// 修改顺序 --
export async function updateOrder(data: any) {
  return usePut('/banner/update/sort/order', JSON.stringify(data)).catch(resp => message.error(resp))
}
