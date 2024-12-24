# 我是一级标题

​	哈哈哈哈

{% asset_img image-20241222102428657.png %}

## 我是二级标题

{% asset_img image-20241222102445069.png %}

# 我是一级标题

```vue
<template>
    <div v-if="article" class="article-detail">
        <el-card class="article-card">
            <h1 class="article-title">{{ article.title }}</h1>
            <div class="article-meta">
                <span class="article-date">{{ article.date }}</span>
                <span class="article-category">{{ article.category }}</span>
            </div>
            <p class="article-description">{{ article.description }}</p>
            <div class="article-content">{{ article.content }}</div>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

// 定义 Article 类型
interface Article {
    title: string;
    description: string;
    content: string;
    date: string;
    category: string;
}

// 获取路由信息
const route = useRoute();
const articleId = route.query.id as string; // 获取传递的文章 ID

// 文章内容
const article = ref<Article | null>(null);

// 根据 ID 从后端获取文章内容
onMounted(async () => {
    if (articleId) {
        const articleData = await fetchArticleData(articleId);
        article.value = articleData;
    }
});

// 模拟获取文章数据的函数（这里可以替换成实际的 API 调用）
const fetchArticleData = async (id: string): Promise<Article> => {
    const fakeData: Record<string, Article> = {
        '1': { title: '文章标题 1', date: '2024-12-20', category: '前端开发', description: '这是文章1的描述', content: '这是一篇长文章的内容' },
        '2': { title: '文章标题 2', date: '2024-12-21', category: '后端开发', description: '这是文章2的描述', content: '这是一篇长文章的内容' },
    };
    return fakeData[id] || { title: '', date: '', category: '', description: '', content: '' };
};
</script>

<style scoped>
.article-detail {
    display: flex;
    justify-content: center;
    align-items: flex-start; /* 改为 flex-start，确保卡片从顶部开始排列 */
    min-height: 100vh; /* 确保内容居中显示 */
    padding-top: 50px; /* 调整此值来让卡片向下移动 */
}

.article-card {
    width: 100%;
    max-width: 800px; /* 控制卡片宽度 */
    margin: 20px;
    padding: 20px;
    background-color: white;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.article-title {
    font-size: 2rem;
    margin-bottom: 10px;
    color: #333;
}

.article-meta {
    font-size: 0.9rem;
    color: #888;
    margin-bottom: 20px;
}

.article-description {
    font-size: 1.1rem;
    margin-bottom: 20px;
    color: #666;
}

.article-content {
    font-size: 1rem;
    line-height: 1.6;
    color: #444;
}
</style>

```

