<template>
    <div>
        <div class="simple-image-preview" @click="editCropper()">
            <img v-if="options.img" :src="options.img" class="preview-img" />
            <div v-else class="empty-preview">
                <i class="el-icon-plus"></i>
                <div class="text">点击上传图片</div>
            </div>
        </div>
        <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body @opened="modalOpened"
            @close="closeDialog">
            <div class="simple-upload-content">
                <div class="upload-area" v-if="!options.img">
                    <el-upload action="#" :http-request="requestUpload" :show-file-list="false"
                        :before-upload="beforeUpload" name="avatarfile" drag>
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em></div>
                        <div class="el-upload__tip">支持JPG、PNG、JPEG格式，文件大小不超过5MB</div>
                    </el-upload>
                </div>
                <div class="preview-area" v-else>
                    <img :src="options.img" alt="预览图片" />
                    <div class="preview-actions">
                        <el-button @click="selectNewImage">重新选择</el-button>
                        <el-button type="primary" @click="uploadImg()">确认上传</el-button>
                    </div>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import request from '@/utils/request'

export default {
    name: "SimpleImageUpload",
    props: {
        value: {
            type: String,
            default: ""
        },
        title: {
            type: String,
            default: "上传图片"
        }
    },
    data() {
        return {
            // 是否显示弹出层
            open: false,
            options: {
                img: this.getImageUrl(this.value),  //图片地址
                file: null,  // 原始文件
                filename: ''          // 文件名称
            }
        }
    },
    watch: {
        value(newVal) {
            this.options.img = this.getImageUrl(newVal)
        }
    },
    methods: {
        // 获取图片完整URL
        getImageUrl(imagePath) {
            if (!imagePath) return '';
            if (imagePath.startsWith('http')) {
                return imagePath;
            }
            // 拼接服务器地址
            return process.env.VUE_APP_BASE_API + imagePath;
        },
        // 编辑图片
        editCropper() {
            this.open = true
        },
        // 打开弹出层结束时的回调
        modalOpened() {
        },
        // 覆盖默认的上传行为
        requestUpload() {
        },
        // 选择新图片
        selectNewImage() {
            this.options.img = ''
            this.options.file = null
        },
        // 上传预处理
        beforeUpload(file) {
            if (file.type.indexOf("image/") == -1) {
                this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG，JPEG后缀的文件。")
                return false
            } else if (file.size / 1024 / 1024 > 5) {
                this.$modal.msgError("文件大小不能超过5MB。")
                return false
            } else {
                const reader = new FileReader()
                reader.readAsDataURL(file)
                reader.onload = () => {
                    this.options.img = reader.result
                    this.options.file = file
                    this.options.filename = file.name
                }
                return false // 阻止自动上传
            }
        },
        // 上传图片
        uploadImg() {
            if (!this.options.file) {
                this.$modal.msgError("请先选择图片")
                return
            }

            let formData = new FormData()
            formData.append("avatarfile", this.options.file, this.options.filename)

            // 使用广告图片上传接口
            request({
                url: '/public/admin/upload/advertisement',
                method: 'post',
                data: formData,
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(response => {
                this.open = false
                // 更新图片URL
                this.options.img = this.getImageUrl(response.imgUrl)
                // 触发v-model更新
                this.$emit('input', response.imgUrl)
                this.$modal.msgSuccess("上传成功")
            }).catch(error => {
                this.$modal.msgError("上传失败：" + (error.message || "未知错误"))
            })
        },
        // 关闭窗口
        closeDialog() {
            this.options.img = this.getImageUrl(this.value)
            this.options.file = null
        }
    }
}
</script>

<style scoped lang="scss">
.simple-image-preview {
    position: relative;
    display: inline-block;
    width: 120px;
    height: 80px;
    border: 2px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    transition: border-color 0.3s;
    overflow: hidden;

    &:hover {
        border-color: #409eff;
    }

    .preview-img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 4px;
    }

    .empty-preview {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100%;
        color: #8c939d;

        .el-icon-plus {
            font-size: 20px;
            margin-bottom: 4px;
        }

        .text {
            font-size: 12px;
        }
    }
}

.simple-image-preview:hover .empty-preview {
    color: #409eff;
}

.simple-image-preview:hover:after {
    content: '';
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
}

.simple-image-preview:hover:before {
    content: '点击上传';
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    color: #fff;
    font-size: 12px;
    z-index: 1;
    pointer-events: none;
}

.simple-upload-content {
    min-height: 200px;

    .upload-area {
        .el-upload-dragger {
            width: 100%;
            height: 180px;
        }

        .el-upload__tip {
            margin-top: 10px;
            color: #909399;
            font-size: 12px;
        }
    }

    .preview-area {
        text-align: center;

        img {
            max-width: 100%;
            max-height: 300px;
            border-radius: 4px;
            box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .preview-actions {
            display: flex;
            justify-content: center;
            gap: 10px;
        }
    }
}
</style>
