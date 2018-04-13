工作中遇到需求，需要给海报切圆角，一开始直接编辑 Bitmap 完成切角，后面又在自定义 View 里面去画圆角图片。

后面需求又变了，好几个 View 叠加起来有个切角。

本着一劳永逸的精神，写了这个控件，是一个切圆角的 ViewGroup，给 ViewGroup 设定切角属性后，里面的 View 都会被切角。

> 就相当于 ViewGroup 的边界是一个切角的边界，所有超过边界的都不会显示。
>
> 会和`clipChildren`属性冲突

# 实际效果

![](https://ws4.sinaimg.cn/large/006tKfTcly1fqava7owkbj30vi0k0q5r.jpg)

# 自定义属性

| 属性名              | 效果                 |
| ------------------- | -------------------- |
| roundAsCircle       | 是否是一个圆形       |
| roundedCornerRadius | 圆角半径             |
| roundTopLeft        | 左上角是否为圆角     |
| roundTopRight       | 右上角是否为圆角     |
| roundBottomLeft     | 左下角是否为圆角     |
| roundBottomRight    | 右下角是否为圆角     |
| roundingBorderWidth | 外边框宽度（未实现） |
| roundingBorderColor | 外边框颜色（未实现） |

