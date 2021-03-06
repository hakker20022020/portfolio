//package uz.inha.portfolio.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import uz.inha.portfolio.model.Attachment;
//import uz.inha.portfolio.model.AttachmentContent;
//import uz.inha.portfolio.repository.AttachmentContentRepository;
//import uz.inha.portfolio.repository.AttachmentRepository;
//import uz.inha.portfolio.repository.UserRepository;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/attachment")
//@RequiredArgsConstructor
//public class AttachmentController {
//
//    private final AttachmentRepository attachmentRepository;
//
//    private final AttachmentContentRepository attachmentContentRepository;
//
//    private final UserRepository userRepository;
//
//    public static final String uploadDirectory = "yuklanganlar";
//
//    @PostMapping("/uploadDb/{id}")
//    public String uploadFileToDb(@PathVariable Integer id, MultipartHttpServletRequest request) throws IOException {
//        Iterator<String> fileNames = request.getFileNames();
//        MultipartFile file = request.getFile(fileNames.next());
//
//        if (file != null) {
//            String originalFilename = file.getOriginalFilename();
//            long size = file.getSize();
//            String contentType = file.getContentType();
//            Attachment attachment = new Attachment();
//            attachment.setContentType(contentType);
//            attachment.setSize(size);
//            attachment.setFileOriginalName(originalFilename);
//            attachment.setUser(userRepository.findById(id).get());
//            Attachment saveAttachment = attachmentRepository.save(attachment);
//
//            AttachmentContent attachmentContent = new AttachmentContent();
//            attachmentContent.setBytes(file.getBytes());
//            attachmentContent.setAttachment(saveAttachment);
//            attachmentContentRepository.save(attachmentContent);
//
//            return "file's id saved:" + attachment.getId();
//
//        }
//
//        return "exception";
//    }
//
//    @GetMapping("/getFileByUserId/{id}")
//    public void getFileByUserId(@PathVariable Integer id, HttpServletResponse response) throws IOException {
//        List<Attachment> attachmentList = attachmentRepository.findAllByUserId(id);
//        getFile(attachmentList.get(0).getId(), response);
//    }
//
//    @PostMapping("/uploadSystem")
//    public String uploadFileToSystem(MultipartHttpServletRequest request) throws IOException {
//        Iterator<String> fileNames = request.getFileNames();
//        MultipartFile file = request.getFile(fileNames.next());
//
//        if (file != null) {
//            String originalFilename = file.getOriginalFilename();
//            Attachment attachment = new Attachment();
//            attachment.setContentType(file.getContentType());
//            attachment.setSize(file.getSize());
//            attachment.setFileOriginalName(file.getOriginalFilename());
//
//            String[] split = originalFilename.split("\\.");
//            String name = UUID.randomUUID().toString() + "." + split[split.length - 1];
//            attachment.setName(name);
//
//            attachmentRepository.save(attachment);
//
//            Path path = Paths.get(uploadDirectory + "/" + name);
//            Files.copy(file.getInputStream(), path);
//            return "Saved id" + attachment.getId();
//        }
//
//        return "xatolik saqlanmadi";
//    }
//
//    @GetMapping("/getFile/{id}")
//    public void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
//        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
//        if (optionalAttachment.isPresent()) {
//            Attachment attachment = optionalAttachment.get();
//
//            Optional<AttachmentContent> byAttachmentId = attachmentContentRepository.findByAttachmentId(attachment.getId());
//            if (byAttachmentId.isPresent()) {
//                AttachmentContent attachmentContent = byAttachmentId.get();
//
//                response.setHeader("Content-Disposition", "attachment; file=\"" + attachment.getFileOriginalName() + "\"");
//
//                response.setContentType(attachment.getContentType());
//
//                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
//
//            }
//        }
//
//    }
//
//    @GetMapping("getFromFileSystem/{id}")
//    public void getFromFileSystem(@PathVariable Integer id, HttpServletResponse response) throws IOException {
//
//        Optional<Attachment> byId = attachmentRepository.findById(id);
//
//        if (byId.isPresent()) {
//            Attachment attachment = byId.get();
//            response.setHeader("Content-Disposition", "attachment; file=\"" + attachment.getFileOriginalName() + "\"");
//
//            response.setContentType(attachment.getContentType());
//            FileInputStream fileInputStream = new FileInputStream(uploadDirectory + "/" + attachment.getName());
//            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
//        }
//    }
//
//}