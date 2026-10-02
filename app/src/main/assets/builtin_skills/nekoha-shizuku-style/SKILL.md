---
name: nekoha-shizuku-style
description: 猫羽雫（Nekoha Shizuku）画风提示词技能。当用户需要生成猫羽雫画风图片、反推该画风提示词、或需要该角色的 Danbooru 风格标签组合时使用。基于 927 条 @Nacho Neko 画师真实标签数据统计，含完整标签分类库。
---

# 猫羽雫（Nekoha Shizuku）画风提示词 Skill

## 描述
本技能包含猫羽雫（Nekoha Shizuku）画风的提示词集合，基于 927 条真实图片标签数据（@Nacho Neko 画师作品标签）统计生成，可用于生成具有该角色及画风特征的图片。

## 数据来源
927 条 img_*.txt 标签文件统计，共 2191 种标签。数据集同时覆盖 SFW 与 NSFW 内容（约一成半含露骨/半露元素）。

## 画师触发（必备）
**927 条数据 100% 带 `@Nacho Neko` 画师触发**，提示词标签层必须包含 `@Nacho Neko`（位于质量词后、角色名前的画师位）。

## 核心标签（必不可少）
**所有提示词必须以 `Nekoha Shizuku, @Nacho Neko` 开头**，以下为出现率基于 927 条真实数据统计：

### 角色基础
- `Nekoha Shizuku` - 角色触发（100%）
- `@Nacho Neko` - 画师触发（100%）
- `bangs` - 刘海（98.1%）
- `looking at viewer` - 看着观众（96.3%）
- `long hair` - 长发（95.7%）
- `1girl` - 单个女性角色（95.0%）
- `solo` - 独自一人（94.9%）
- `blush` - 脸红（94.0%）
- `cat ears` - 猫耳（92.3%）
- `blue eyes` - 蓝色眼睛（83.7%）
- `soft lighting` - 柔和光线（71.6%）
- `cat tail` - 猫尾（62.0%）
- `blue hair` - 蓝色头发（56.7%）
- `hair ornament` / `hair orb` - 头饰（42.8%）
- `twintails` - 双马尾（20.5%）

### 画面取景
- `upper body` - 上半身（60.5%）
- `indoors` - 室内（50.2%）
- `white background` - 白色背景（31.6%）
- `full body` - 全身（26.5%）
- `sitting` - 坐姿（26.0%）
- `simple background` - 简洁背景（22.1%）
- `bed` / `pillow` - 床/枕头居家场景（21.5%/19.3%）
- `close-up` / `close up` - 特写（16.9%/6.4%）
- `lying down` / `lying` - 躺卧（13.1%/5.8%）
- `outdoors` - 户外（12.0%）
- `window` - 窗户（10.2%）
- `front view` / `side view` - 正面/侧面（10.0%/6.4%）
- `low angle` / `high angle` - 仰视/俯视（8.3%/5.2%）

### 表情特征
- `open mouth` - 张嘴（55.1%）
- `smile` - 微笑（24.9%）
- `neutral expression` - 面无表情（7.7%）
- `sweat` - 汗（8.3%）
- `eyes closed` / `closed eyes` - 闭眼（2.8%）
- `winking` - 眨眼（2.2%）
- `pout` - 噘嘴（1.9%）

### 服装元素
- `white shirt` - 白衬衫（23.1%）
- `white dress` - 白连衣裙（12.2%）
- `off shoulder` - 一字肩（11.9%）
- `maid` + `maid headdress` + `white apron` / `black dress` - 女仆装（9.3%/8.1%/9.7%/8.5%）
- `choker` - 项圈（14.5%）
- `bow` / `ribbon` - 蝴蝶结/丝带（7.9%/7.9%）
- `white stockings` / `white socks` / `white thighhighs` - 白袜系（8.1%/6.7%/4.1%）
- `thighhighs` - 过膝袜（5.5%）
- `black pantyhose` / `white pantyhose` - 裤袜（4.6%/4.1%）
- `bare shoulders` / `off shoulder` - 露肩（8.3%/11.9%）
- `bare legs` / `bare feet` - 裸腿/裸足（11.8%/5.6%）
- `sailor uniform` - 水手服（3.3%）
- `white sweater` / `black hoodie` - 毛衣/卫衣（3.3%/2.8%）
- `kimono` - 和服（3.2%）
- `white panties` / `underwear` / `white bra` - 内衣（4.0%/2.9%/2.3%）
- `bikini` - 比基尼（2.9%）

---

## 标签分类库（按新数据修正补充）

### 标准提示词结构
```
masterpiece, best quality, score_7, <safety>, Nekoha Shizuku, @Nacho Neko, 1girl, solo,
<角色/外观标签>, <服装标签>, <姿势表情标签>, <场景标签>, <取景标签>
```
- safety 分级：SFW 用 `safe`，露骨内容用 `nsfw`
- `@Nacho Neko` 固定在角色名之前（画师位）
- 反推场景若图中含 NSFW 元素，标签原样保留（exposed_nipples/nude 等）

### 高频装饰细节
`hair_orb`, `hair_ornament`, `hairclip`, `hairpin`, `hair_flower`, `hair_tie`, `hair_ribbon`,
`blue_bow`, `black_bow`, `red_bow`, `white_bow`, `black_ribbon`, `blue_ribbon`, `red_ribbon`,
`pearl`, `bell`, `collar`, `frills`, `frilled_skirt`, `frilled_sleeves`, `bow`, `ribbon`

### 居家/床场景系
`bed`, `on_bed`, `pillow`, `white_sheets`, `blanket`, `lying`, `lying_down`, `lying_on_back`,
`on_back`, `legs_apart`, `legs_up`, `sitting`, `kneeling`, `white_background`, `simple_background`

### 手持物系
`plushie`, `cat_plushie`, `blue_plushie`, `holding_plushie`, `cake`, `food`, `fork`,
`holding_plate`, `hand_on_chin`, `hand_on_chest`, `hand_on_thigh`, `cat`, `black_cat`, `paw_prints`

### 发色变体系（非蓝发测试可用）
`blue_hair`, `grey_hair`, `gray_hair`, `white_hair`, `black_hair`, `pink_hair`, `blonde_hair`,
`green_hair`, `light_blue_hair`, `red_eyes`, `green_eyes`, `purple_eyes`, `heterochromia`,
`short_hair`, `twintails`, `twin_tails`, `side_hair`

### NSFW 相关标签（按出现率排序，927 基数）
```
exposed_nipples (11.0%), nude (10.6%), exposed_breasts (9.5%), exposed_legs (10.1%),
exposed_belly (14.0%), exposed_thighs (13.4%), cleavage (4.2%), topless (3.5%),
exposed_buttocks (3.3%), exposed_shoulders/bare_shoulders (8.3%/7.9%), wet (2.4%),
nipples (2.8%), bare_chest (5.2%), exposed_chest (2.8%), areola (2.0%),
exposed_genitals (2.0%), exposed_nipple (2.0%), spread_legs (1.8%), underwear (2.9%),
white_panties (4.0%), white_bra (2.3%), open_shirt (4.5%), rope (2.3%), lifting_skirt (1.8%)
```
> 注：`exposed_nipples`（102 张/11%）与 `nude`（98 张/10.6%）是该数据集中露骨程度的标志性标签；生成时按需选择。

---

## 使用方法

### 1. 基础组合（必须包含）
```
Nekoha Shizuku, @Nacho Neko, 1girl, solo, bangs, long hair, cat ears, cat tail, blush, [其他标签]
```

### 2. 标准示例（高频标签组合）
```
masterpiece, best quality, score_7, safe, Nekoha Shizuku, @Nacho Neko, 1girl, solo,
long hair, bangs, looking at viewer, blush, smile, open mouth, blue eyes, cat ears, cat tail,
hair ornament, blue bow, white shirt, choker, upper body, indoors, soft lighting,
simple background, white background
```

### 3. 反推用途
反推出的标签串可直接作为该画风数据集标签使用；若标签缺失 `@Nacho Neko` 或 `Nekoha Shizuku` 触发词，需手动补上。

---

## 注意事项
1. **必须以 `Nekoha Shizuku, @Nacho Neko` 开头** - 角色 + 画师双触发，缺一不可
2. **建议包含核心角色标签** - 1girl, solo, bangs, long hair, cat ears, cat tail, blush
3. **背景建议** - white/simple background 或 indoor（床/窗）更符合该画风
4. **出现率供参考** - 高频标签（>50%）尽量保留，中频按需取舍
5. **NSFW 判定** - 数据集含 NSFW 内容，生成时按用户需求决定 safety 分级与露骨标签
