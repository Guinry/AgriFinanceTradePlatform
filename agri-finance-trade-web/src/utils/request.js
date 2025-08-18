import axios from 'axios'

export function request(config) {
    // 创建axios的实例
    const envURL = import.meta.env.VITE_API_URL;
    const baseURL = envURL || 'http://localhost:9090';

    // 添加更详细的环境变量检查
    if (!envURL) {
        console.warn('未配置环境变量VITE_API_URL，使用默认地址: http://localhost:9090');
        console.warn('请在.env文件中配置VITE_API_URL以指向正确的后端服务地址');
    }

    const instance = axios.create({
        // baseURL: 'http://3958b99l28.zicp.vip',
        // baseURL: 'http://119.3.180.117:9090',
        baseURL: baseURL,
        timeout: 100000
    })

    // 请求拦截器
    instance.interceptors.request.use(config => {
        return config
    }, err => { })

    // 响应拦截器
    instance.interceptors.response.use(
        res => {
            // 根据实际的响应结构进行调整
            const { code, message } = res.data;
            console.log('后端返回的数据:', res.data); // 打印响应内容，检查 `code` 和 `message`
            // 根据后端约定的状态码进行处理
            if (code === 20000) {
                return res.data;
            } else {
                // 处理业务错误
                console.error('业务错误:', message);
                return Promise.reject(new Error(message || 'Error'));
            }
        },
        err => {
            console.error('网络错误:', err);
            // 可以在这里统一处理网络错误，如显示通知等
            if (err.response) {
                const { status } = err.response;
                switch (status) {
                    case 401:
                        // 未授权，跳转到登录页
                        console.error('未授权，请重新登录');
                        break;
                    case 403:
                        console.error('拒绝访问');
                        break;
                    case 404:
                        console.error('请求资源不存在');
                        break;
                    case 500:
                        console.error('服务器内部错误');
                        break;
                    default:
                        console.error(`连接错误${status}`);
                }
            } else if (err.request) {
                // 请求已发出但没有收到响应
                console.error('网络连接异常：无法连接到服务器，请检查:');
                console.error('- 后端服务是否正在运行');
                console.error('- API地址配置是否正确:', baseURL);
                console.error('- 网络连接是否正常');
                console.error('- 如果是本地开发，请确认后端服务是否启动在9090端口');
                console.error('- 如果是远程服务器，请确认服务器地址和端口是否正确');
            } else {
                console.error('请求配置错误:', err.message);
            }
            return Promise.reject(err);
        }
    );

    // 3.发送真正的网络请求
    return instance(config)
}