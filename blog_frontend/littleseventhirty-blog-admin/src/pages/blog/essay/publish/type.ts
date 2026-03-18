// 文章分类
export interface CategoryType {
  categoryId: string
  categoryName: string
}

// 文章标签
export interface TagType {
  tagId: number
  tagName: string
}

// 提交数据
export interface ArticleType {
  categoryId: string
  tagId: string[]
  articleTitle: string
  articleCover: string
  articleContent: string
  articleType: number
  isTop: number
  status: number
  articleId?: string
}
