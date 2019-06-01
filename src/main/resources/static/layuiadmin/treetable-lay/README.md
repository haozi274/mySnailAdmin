# treetable-lay
&emsp;实现layui的树形表格treeTable

## 1.简介
&emsp;在layui数据表格之上进行扩展实现。

- 演示地址：[https://whvse.gitee.io/treetable-lay/](https://whvse.gitee.io/treetable-lay/)


&emsp;还有一个BOM表结构的树形表格，[树形表格2](https://gitee.com/whvse/treetable)，欢迎查看。

## 2.使用方法

### 2.1.引入模块
&emsp;下载module/treetable-lay整个文件夹，放在你的项目里面，然后使用模块加载的方式使用：
```javascript
layui.config({
    base: 'module/'
}).extend({
    treetable: 'treetable-lay/treetable'
}).use(['treetable'], function () {
    var treetable = layui.treetable;
    
});
```

### 2.2.渲染表格
```html
<table id="table1" class="layui-table" lay-filter="table1"></table>

<script>
layui.use(['treetable'], function () {
    var treetable = layui.treetable;
    
    // 渲染表格
    treetable.render({
        treeColIndex: 2,          // treetable新增参数
        treeSpid: -1,             // treetable新增参数
        treeIdName: 'd_id',       // treetable新增参数
        treePidName: 'd_pid',     // treetable新增参数
        treeDefaultClose: true,   // treetable新增参数
        treeLinkage: true,        // treetable新增参数
        elem: '#table1',
        url: 'json/data1.json',
        cols: [[
            {type: 'numbers'},
            {field: 'id', title: 'id'},
            {field: 'name', title: 'name'},
            {field: 'sex', title: 'sex'},
            {field: 'pid', title: 'pid'},
        ]]
    });
});
</script>

```

> 注意：<br>
> &emsp;&emsp;可以使用url传递数据，也可以使用data传递数据，如果使用url传递数据，参数是where字段，
> 跟layui数据表格的使用方式一致。

<br/>

&emsp;**数据格式**

&emsp;&emsp;总而言之就是以id、pid的形式，不是以subMenus的形式，当然id、pid这两个字段的名称可以自定义：
```json
{
  "code": 0,
  "msg": "ok",
  "data": [{
      "id": 1,
      "name": "xx",
      "sex": "male",
      "pid": -1
    },{
      "id": 2,
      "name": "xx",
      "sex": "male",
      "pid": 1
    }
  ]
}
```

### 2.3.参数说明
&emsp;layui数据表格的所有参数都可以用，除此之外treetable新增的参数有：

 参数 | 类型 | 是否必填 | 描述 |
 --- | --- | --- | ---
 treeColIndex | int | 是 | 树形图标显示在第几列
 treeSpid | object | 是 | 最上级的父级id
 treeIdName | string | 否 | id字段的名称
 treePidName | string | 否 | pid字段的名称
 treeDefaultClose | boolean | 否 | 是否默认折叠
 treeLinkage | boolean | 否 | 父级展开时是否自动展开所有子级


&emsp;**treeColIndex**

&emsp;树形图标（箭头和文件夹、文件的图标）显示在第几列， 索引值是cols数组的下标。

&emsp;**treeSpid**

&emsp;最上级的父级id，比如你可以规定pid为0或-1的是最顶级的目录。
 
&emsp;**treeIdName**

&emsp;treetable是以id和pid字段来渲染树形结构的，如果你的数据没有id和pid字段，你可以指定id和pid字段的名称。

&emsp;**treePidName**

&emsp;pid在你的数据字段中的名称。


&emsp;**treeDefaultClose**

&emsp;默认是全部展开的，如果需要默认全部关闭，加上treeDefaultClose:true即可。

&emsp;**treeLinkage**

&emsp;父级展开时是否自动展开所有子级


### 2.4.注意事项

- 不能使用分页功能，即使写了page:true，也会忽略该参数。

- 不能使用排序功能，不要开启排序功能。
- table.reload()不能实现刷新，请参考demo的刷新。
- 除了文档上写的treetable.xxx的方法之外，其他数据表格的方法都使用table.xxx。
- 建议删除和修改请求完后台之后请刷新（重新渲染）表格，最好不要使用obj.delete方式删除。

### 2.5.其他方法

&emsp;**全部展开**
```javascript
treetable.expandAll('#table1');
```
 
&emsp;**全部折叠**
 ```javascript
treetable.foldAll('#table1');
```

### 2.6.如何修改图标

&emsp;&emsp;通过css来修改图标，content是图标的unicode字符。

&emsp;**修改文件夹图标：**
```css
/** 未展开 */
.treeTable-icon .layui-icon-layer:before {
    content: "\e638";
}

/** 展开 */
.treeTable-icon.open .layui-icon-layer:before {
    content: "\e638";
}
```

&emsp;**修改文件图标：**
```css
.treeTable-icon .layui-icon-file:before {
    content: "\e621";
}
```

&emsp;**修改箭头的图标：**
```css
/** 未展开 */
.treeTable-icon .layui-icon-triangle-d:before {
    content: "\e623";
}

/** 展开 */
.treeTable-icon.open .layui-icon-triangle-d:before {
    content: "\e625";
}
```

&emsp;**如何获取content：**

![](https://ws1.sinaimg.cn/large/006a7GCKgy1ftjutx5bk0j30pq0ht40b.jpg)



## 2.7.截图

&emsp;树形表格1：

![树形表格1](https://ws1.sinaimg.cn/large/006a7GCKly1ftisynlfq0j30ng0g3t9b.jpg)

&emsp;树形表格2：

![树形表格2](https://ws1.sinaimg.cn/large/006a7GCKgy1ftgdebdnsmj30ux0qktbc.jpg)
